package com.palantir.paxos;

import com.google.common.base.Defaults;
import com.google.common.base.Preconditions;
import com.google.protobuf.InvalidProtocolBufferException;
import com.palantir.common.annotation.Immutable;
import com.palantir.common.base.Throwables;
import com.palantir.common.persist.Persistable;
import com.palantir.paxos.persistence.generated.PaxosPersistence;

/**
 * The logged state (per round) for a paxos acceptor.
 *
 * @author rullman
 *
 * @param <T> the type of the state agreed on for this round
 */
@Immutable
public class PaxosAcceptorState implements Persistable, Versionable {
    final PaxosProposalId lastPromisedId; // latest promised id
    final PaxosProposalId lastAcceptedId; // latest accepted id
    final PaxosValue lastAcceptedValue; // latest accepted value, null if no accepted value
    final long version;

    public static final Hydrator<PaxosAcceptorState> BYTES_HYDRATOR = new Hydrator<PaxosAcceptorState>() {
        @Override
        public PaxosAcceptorState hydrateFromBytes(byte[] input) {
            try {
                PaxosPersistence.PaxosAcceptorState message = PaxosPersistence.PaxosAcceptorState.parseFrom(input);
                return hydrateFromProto(message);
            } catch (InvalidProtocolBufferException e) {
                throw Throwables.throwUncheckedException(e);
            }
        }
    };

    public static PaxosAcceptorState newState(PaxosProposalId pid) {
        return new PaxosAcceptorState(pid);
    }

    public static PaxosAcceptorState newState(PaxosProposal proposal) {
        return new PaxosAcceptorState(proposal.id, proposal.id, proposal.val, 0L);
    }

    private PaxosAcceptorState(PaxosProposalId pid) {
        this.lastPromisedId = Preconditions.checkNotNull(pid);
        this.lastAcceptedId = null;
        this.lastAcceptedValue = null;
        this.version = 0L;
    }

    private PaxosAcceptorState(PaxosProposalId pid,
                               PaxosProposalId aid,
                               PaxosValue val,
                               long version) {
        this.lastPromisedId = Preconditions.checkNotNull(pid);
        this.lastAcceptedId = aid;
        this.lastAcceptedValue = val;
        this.version = version;
    }

    public PaxosAcceptorState withPromise(PaxosProposalId pid) {
        return new PaxosAcceptorState(pid, lastAcceptedId, lastAcceptedValue, version + 1);
    }

    public PaxosAcceptorState withState(PaxosProposalId pid,
                                      PaxosProposalId aid,
                                      PaxosValue val) {
        return new PaxosAcceptorState(pid, aid, val, version + 1);
    }

    @Override
    public byte[] persistToBytes() {
        PaxosPersistence.PaxosAcceptorState.Builder b = PaxosPersistence.PaxosAcceptorState.newBuilder();
        if (lastPromisedId != null) {
            b.setLastPromisedId(lastPromisedId.persistToProto());
        }
        if (lastAcceptedId != null) {
            b.setLastAcceptedId(lastAcceptedId.persistToProto())
             .setLastAcceptedValue(lastAcceptedValue.persistToProto());
        }
        return b.build().toByteArray();
    }

    public static PaxosAcceptorState hydrateFromProto(PaxosPersistence.PaxosAcceptorState message) {
        PaxosProposalId pid = null;
        if (message.hasLastPromisedId()) {
            pid = PaxosProposalId.hydrateFromProto(message.getLastPromisedId());
        }
        PaxosProposalId aid = null;
        if (message.hasLastAcceptedId()) {
            aid = PaxosProposalId.hydrateFromProto(message.getLastAcceptedId());
        }
        PaxosValue val = null;
        if (message.hasLastAcceptedValue()) {
            val = PaxosValue.hydrateFromProto(message.getLastAcceptedValue());
        }
        return new PaxosAcceptorState(pid, aid, val, Defaults.defaultValue(long.class));
    }

    @Override
    public long getVersion() {
        return version;
    }
}