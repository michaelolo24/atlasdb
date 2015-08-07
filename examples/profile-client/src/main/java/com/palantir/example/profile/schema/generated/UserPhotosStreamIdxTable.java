/**
 * Copyright 2015 Palantir Technologies
 *
 * Licensed under the BSD-3 License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://opensource.org/licenses/BSD-3-Clause
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.palantir.example.profile.schema.generated;

import java.util.Arrays;
import java.util.Collection;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedMap;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;



import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.base.Optional;
import com.google.common.base.Supplier;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Collections2;
import com.google.common.collect.ComparisonChain;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import com.google.common.collect.Sets;
import com.google.common.primitives.Bytes;
import com.google.common.primitives.UnsignedBytes;
import com.google.protobuf.InvalidProtocolBufferException;
import com.palantir.atlasdb.compress.CompressionUtils;
import com.palantir.atlasdb.encoding.PtBytes;
import com.palantir.atlasdb.keyvalue.api.Cell;
import com.palantir.atlasdb.keyvalue.api.ColumnSelection;
import com.palantir.atlasdb.keyvalue.api.Prefix;
import com.palantir.atlasdb.keyvalue.api.RangeRequest;
import com.palantir.atlasdb.keyvalue.api.RowResult;
import com.palantir.atlasdb.keyvalue.impl.Cells;
import com.palantir.atlasdb.ptobject.EncodingUtils;
import com.palantir.atlasdb.table.api.AtlasDbDynamicMutableExpiringTable;
import com.palantir.atlasdb.table.api.AtlasDbDynamicMutablePersistentTable;
import com.palantir.atlasdb.table.api.AtlasDbMutableExpiringTable;
import com.palantir.atlasdb.table.api.AtlasDbMutablePersistentTable;
import com.palantir.atlasdb.table.api.AtlasDbNamedExpiringSet;
import com.palantir.atlasdb.table.api.AtlasDbNamedMutableTable;
import com.palantir.atlasdb.table.api.AtlasDbNamedPersistentSet;
import com.palantir.atlasdb.table.api.ColumnValue;
import com.palantir.atlasdb.table.api.TypedRowResult;
import com.palantir.atlasdb.table.description.ColumnValueDescription.Compression;
import com.palantir.atlasdb.table.generation.ColumnValues;
import com.palantir.atlasdb.table.generation.Descending;
import com.palantir.atlasdb.table.generation.NamedColumnValue;
import com.palantir.atlasdb.transaction.api.AtlasDbConstraintCheckingMode;
import com.palantir.atlasdb.transaction.api.ConstraintCheckingTransaction;
import com.palantir.atlasdb.transaction.api.Transaction;
import com.palantir.common.base.AbortingVisitor;
import com.palantir.common.base.AbortingVisitors;
import com.palantir.common.base.BatchingVisitable;
import com.palantir.common.base.BatchingVisitableView;
import com.palantir.common.base.BatchingVisitables;
import com.palantir.common.base.Throwables;
import com.palantir.common.collect.IterableView;
import com.palantir.common.persist.Persistable;
import com.palantir.common.persist.Persistable.Hydrator;
import com.palantir.common.persist.Persistables;
import com.palantir.common.proxy.AsyncProxy;
import com.palantir.util.AssertUtils;
import com.palantir.util.crypto.Sha256Hash;


public final class UserPhotosStreamIdxTable implements
        AtlasDbDynamicMutablePersistentTable<UserPhotosStreamIdxTable.UserPhotosStreamIdxRow,
                                                UserPhotosStreamIdxTable.UserPhotosStreamIdxColumn,
                                                UserPhotosStreamIdxTable.UserPhotosStreamIdxColumnValue,
                                                UserPhotosStreamIdxTable.UserPhotosStreamIdxRowResult> {
    private final Transaction t;
    private final List<UserPhotosStreamIdxTrigger> triggers;
    private final static String tableName = "user_photos_stream_idx";

    static UserPhotosStreamIdxTable of(Transaction t) {
        return new UserPhotosStreamIdxTable(t, ImmutableList.<UserPhotosStreamIdxTrigger>of());
    }

    static UserPhotosStreamIdxTable of(Transaction t, UserPhotosStreamIdxTrigger trigger, UserPhotosStreamIdxTrigger... triggers) {
        return new UserPhotosStreamIdxTable(t, ImmutableList.<UserPhotosStreamIdxTrigger>builder().add(trigger).add(triggers).build());
    }

    static UserPhotosStreamIdxTable of(Transaction t, List<UserPhotosStreamIdxTrigger> triggers) {
        return new UserPhotosStreamIdxTable(t, triggers);
    }

    private UserPhotosStreamIdxTable(Transaction t, List<UserPhotosStreamIdxTrigger> triggers) {
        this.t = t;
        this.triggers = triggers;
    }

    public static String getTableName() {
        return tableName;
    }

    /**
     * <pre>
     * UserPhotosStreamIdxRow {
     *   {@literal Long id};
     * }
     * </pre>
     */
    public static final class UserPhotosStreamIdxRow implements Persistable, Comparable<UserPhotosStreamIdxRow> {
        private final long id;

        public static UserPhotosStreamIdxRow of(long id) {
            return new UserPhotosStreamIdxRow(id);
        }

        private UserPhotosStreamIdxRow(long id) {
            this.id = id;
        }

        public long getId() {
            return id;
        }

        public static Function<UserPhotosStreamIdxRow, Long> getIdFun() {
            return new Function<UserPhotosStreamIdxRow, Long>() {
                @Override
                public Long apply(UserPhotosStreamIdxRow row) {
                    return row.id;
                }
            };
        }

        public static Function<Long, UserPhotosStreamIdxRow> fromIdFun() {
            return new Function<Long, UserPhotosStreamIdxRow>() {
                @Override
                public UserPhotosStreamIdxRow apply(Long row) {
                    return new UserPhotosStreamIdxRow(row);
                }
            };
        }

        @Override
        public byte[] persistToBytes() {
            byte[] idBytes = EncodingUtils.encodeUnsignedVarLong(id);
            return EncodingUtils.add(idBytes);
        }

        public static final Hydrator<UserPhotosStreamIdxRow> BYTES_HYDRATOR = new Hydrator<UserPhotosStreamIdxRow>() {
            @Override
            public UserPhotosStreamIdxRow hydrateFromBytes(byte[] __input) {
                int __index = 0;
                Long id = EncodingUtils.decodeUnsignedVarLong(__input, __index);
                __index += EncodingUtils.sizeOfUnsignedVarLong(id);
                return of(id);
            }
        };

        @Override
        public String toString() {
            return MoreObjects.toStringHelper(this)
                .add("id", id)
                .toString();
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            UserPhotosStreamIdxRow other = (UserPhotosStreamIdxRow) obj;
            return Objects.equal(id, other.id);
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(id);
        }

        @Override
        public int compareTo(UserPhotosStreamIdxRow o) {
            return ComparisonChain.start()
                .compare(this.id, o.id)
                .result();
        }
    }

    /**
     * <pre>
     * UserPhotosStreamIdxColumn {
     *   {@literal byte[] reference};
     * }
     * </pre>
     */
    public static final class UserPhotosStreamIdxColumn implements Persistable, Comparable<UserPhotosStreamIdxColumn> {
        private final byte[] reference;

        public static UserPhotosStreamIdxColumn of(byte[] reference) {
            return new UserPhotosStreamIdxColumn(reference);
        }

        private UserPhotosStreamIdxColumn(byte[] reference) {
            this.reference = reference;
        }

        public byte[] getReference() {
            return reference;
        }

        public static Function<UserPhotosStreamIdxColumn, byte[]> getReferenceFun() {
            return new Function<UserPhotosStreamIdxColumn, byte[]>() {
                @Override
                public byte[] apply(UserPhotosStreamIdxColumn row) {
                    return row.reference;
                }
            };
        }

        public static Function<byte[], UserPhotosStreamIdxColumn> fromReferenceFun() {
            return new Function<byte[], UserPhotosStreamIdxColumn>() {
                @Override
                public UserPhotosStreamIdxColumn apply(byte[] row) {
                    return new UserPhotosStreamIdxColumn(row);
                }
            };
        }

        @Override
        public byte[] persistToBytes() {
            byte[] referenceBytes = EncodingUtils.encodeSizedBytes(reference);
            return EncodingUtils.add(referenceBytes);
        }

        public static final Hydrator<UserPhotosStreamIdxColumn> BYTES_HYDRATOR = new Hydrator<UserPhotosStreamIdxColumn>() {
            @Override
            public UserPhotosStreamIdxColumn hydrateFromBytes(byte[] __input) {
                int __index = 0;
                byte[] reference = EncodingUtils.decodeSizedBytes(__input, __index);
                __index += EncodingUtils.sizeOfSizedBytes(reference);
                return of(reference);
            }
        };

        @Override
        public String toString() {
            return MoreObjects.toStringHelper(this)
                .add("reference", reference)
                .toString();
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            UserPhotosStreamIdxColumn other = (UserPhotosStreamIdxColumn) obj;
            return Objects.equal(reference, other.reference);
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(reference);
        }

        @Override
        public int compareTo(UserPhotosStreamIdxColumn o) {
            return ComparisonChain.start()
                .compare(this.reference, o.reference, UnsignedBytes.lexicographicalComparator())
                .result();
        }
    }

    public interface UserPhotosStreamIdxTrigger {
        public void putUserPhotosStreamIdx(Multimap<UserPhotosStreamIdxRow, ? extends UserPhotosStreamIdxColumnValue> newRows);
    }

    /**
     * <pre>
     * Column name description {
     *   {@literal byte[] reference};
     * } 
     * Column value description {
     *   type: Long;
     * }
     * </pre>
     */
    public static final class UserPhotosStreamIdxColumnValue implements ColumnValue<Long> {
        private final UserPhotosStreamIdxColumn columnName;
        private final Long value;

        public static UserPhotosStreamIdxColumnValue of(UserPhotosStreamIdxColumn columnName, Long value) {
            return new UserPhotosStreamIdxColumnValue(columnName, value);
        }

        private UserPhotosStreamIdxColumnValue(UserPhotosStreamIdxColumn columnName, Long value) {
            this.columnName = columnName;
            this.value = value;
        }

        public UserPhotosStreamIdxColumn getColumnName() {
            return columnName;
        }

        @Override
        public Long getValue() {
            return value;
        }

        @Override
        public byte[] persistColumnName() {
            return columnName.persistToBytes();
        }

        @Override
        public byte[] persistValue() {
            byte[] bytes = EncodingUtils.encodeUnsignedVarLong(value);
            return CompressionUtils.compress(bytes, Compression.NONE);
        }

        public static Long hydrateValue(byte[] bytes) {
            bytes = CompressionUtils.decompress(bytes, Compression.NONE);
            return EncodingUtils.decodeUnsignedVarLong(bytes, 0);
        }

        public static Function<UserPhotosStreamIdxColumnValue, UserPhotosStreamIdxColumn> getColumnNameFun() {
            return new Function<UserPhotosStreamIdxColumnValue, UserPhotosStreamIdxColumn>() {
                @Override
                public UserPhotosStreamIdxColumn apply(UserPhotosStreamIdxColumnValue columnValue) {
                    return columnValue.getColumnName();
                }
            };
        }

        public static Function<UserPhotosStreamIdxColumnValue, Long> getValueFun() {
            return new Function<UserPhotosStreamIdxColumnValue, Long>() {
                @Override
                public Long apply(UserPhotosStreamIdxColumnValue columnValue) {
                    return columnValue.getValue();
                }
            };
        }
    }

    public static final class UserPhotosStreamIdxRowResult implements TypedRowResult {
        private final UserPhotosStreamIdxRow rowName;
        private final ImmutableSet<UserPhotosStreamIdxColumnValue> columnValues;

        public static UserPhotosStreamIdxRowResult of(RowResult<byte[]> rowResult) {
            UserPhotosStreamIdxRow rowName = UserPhotosStreamIdxRow.BYTES_HYDRATOR.hydrateFromBytes(rowResult.getRowName());
            Set<UserPhotosStreamIdxColumnValue> columnValues = Sets.newHashSetWithExpectedSize(rowResult.getColumns().size());
            for (Entry<byte[], byte[]> e : rowResult.getColumns().entrySet()) {
                UserPhotosStreamIdxColumn col = UserPhotosStreamIdxColumn.BYTES_HYDRATOR.hydrateFromBytes(e.getKey());
                Long value = UserPhotosStreamIdxColumnValue.hydrateValue(e.getValue());
                columnValues.add(UserPhotosStreamIdxColumnValue.of(col, value));
            }
            return new UserPhotosStreamIdxRowResult(rowName, ImmutableSet.copyOf(columnValues));
        }

        private UserPhotosStreamIdxRowResult(UserPhotosStreamIdxRow rowName, ImmutableSet<UserPhotosStreamIdxColumnValue> columnValues) {
            this.rowName = rowName;
            this.columnValues = columnValues;
        }

        @Override
        public UserPhotosStreamIdxRow getRowName() {
            return rowName;
        }

        public Set<UserPhotosStreamIdxColumnValue> getColumnValues() {
            return columnValues;
        }

        public static Function<UserPhotosStreamIdxRowResult, UserPhotosStreamIdxRow> getRowNameFun() {
            return new Function<UserPhotosStreamIdxRowResult, UserPhotosStreamIdxRow>() {
                @Override
                public UserPhotosStreamIdxRow apply(UserPhotosStreamIdxRowResult rowResult) {
                    return rowResult.rowName;
                }
            };
        }

        public static Function<UserPhotosStreamIdxRowResult, ImmutableSet<UserPhotosStreamIdxColumnValue>> getColumnValuesFun() {
            return new Function<UserPhotosStreamIdxRowResult, ImmutableSet<UserPhotosStreamIdxColumnValue>>() {
                @Override
                public ImmutableSet<UserPhotosStreamIdxColumnValue> apply(UserPhotosStreamIdxRowResult rowResult) {
                    return rowResult.columnValues;
                }
            };
        }
    }

    @Override
    public void delete(UserPhotosStreamIdxRow row, UserPhotosStreamIdxColumn column) {
        delete(ImmutableMultimap.of(row, column));
    }

    @Override
    public void delete(Iterable<UserPhotosStreamIdxRow> rows) {
        Multimap<UserPhotosStreamIdxRow, UserPhotosStreamIdxColumn> toRemove = HashMultimap.create();
        Multimap<UserPhotosStreamIdxRow, UserPhotosStreamIdxColumnValue> result = getRowsMultimap(rows);
        for (Entry<UserPhotosStreamIdxRow, UserPhotosStreamIdxColumnValue> e : result.entries()) {
            toRemove.put(e.getKey(), e.getValue().getColumnName());
        }
        delete(toRemove);
    }

    @Override
    public void delete(Multimap<UserPhotosStreamIdxRow, UserPhotosStreamIdxColumn> values) {
        t.delete(tableName, ColumnValues.toCells(values));
    }

    @Override
    public void put(UserPhotosStreamIdxRow rowName, Iterable<UserPhotosStreamIdxColumnValue> values) {
        put(ImmutableMultimap.<UserPhotosStreamIdxRow, UserPhotosStreamIdxColumnValue>builder().putAll(rowName, values).build());
    }

    @Override
    public void put(UserPhotosStreamIdxRow rowName, UserPhotosStreamIdxColumnValue... values) {
        put(ImmutableMultimap.<UserPhotosStreamIdxRow, UserPhotosStreamIdxColumnValue>builder().putAll(rowName, values).build());
    }

    @Override
    public void put(Multimap<UserPhotosStreamIdxRow, ? extends UserPhotosStreamIdxColumnValue> values) {
        t.useTable(tableName, this);
        t.put(tableName, ColumnValues.toCellValues(values));
        for (UserPhotosStreamIdxTrigger trigger : triggers) {
            trigger.putUserPhotosStreamIdx(values);
        }
    }

    @Override
    public void touch(Multimap<UserPhotosStreamIdxRow, UserPhotosStreamIdxColumn> values) {
        Multimap<UserPhotosStreamIdxRow, UserPhotosStreamIdxColumnValue> currentValues = get(values);
        put(currentValues);
        Multimap<UserPhotosStreamIdxRow, UserPhotosStreamIdxColumn> toDelete = HashMultimap.create(values);
        for (Map.Entry<UserPhotosStreamIdxRow, UserPhotosStreamIdxColumnValue> e : currentValues.entries()) {
            toDelete.remove(e.getKey(), e.getValue().getColumnName());
        }
        delete(toDelete);
    }

    public static ColumnSelection getColumnSelection(Collection<UserPhotosStreamIdxColumn> cols) {
        return ColumnSelection.create(Collections2.transform(cols, Persistables.persistToBytesFunction()));
    }

    public static ColumnSelection getColumnSelection(UserPhotosStreamIdxColumn... cols) {
        return getColumnSelection(Arrays.asList(cols));
    }

    @Override
    public Multimap<UserPhotosStreamIdxRow, UserPhotosStreamIdxColumnValue> get(Multimap<UserPhotosStreamIdxRow, UserPhotosStreamIdxColumn> cells) {
        Set<Cell> rawCells = ColumnValues.toCells(cells);
        Map<Cell, byte[]> rawResults = t.get(tableName, rawCells);
        Multimap<UserPhotosStreamIdxRow, UserPhotosStreamIdxColumnValue> rowMap = HashMultimap.create();
        for (Entry<Cell, byte[]> e : rawResults.entrySet()) {
            if (e.getValue().length > 0) {
                UserPhotosStreamIdxRow row = UserPhotosStreamIdxRow.BYTES_HYDRATOR.hydrateFromBytes(e.getKey().getRowName());
                UserPhotosStreamIdxColumn col = UserPhotosStreamIdxColumn.BYTES_HYDRATOR.hydrateFromBytes(e.getKey().getColumnName());
                Long val = UserPhotosStreamIdxColumnValue.hydrateValue(e.getValue());
                rowMap.put(row, UserPhotosStreamIdxColumnValue.of(col, val));
            }
        }
        return rowMap;
    }

    @Override
    public Multimap<UserPhotosStreamIdxRow, UserPhotosStreamIdxColumnValue> getAsync(final Multimap<UserPhotosStreamIdxRow, UserPhotosStreamIdxColumn> cells, ExecutorService exec) {
        Callable<Multimap<UserPhotosStreamIdxRow, UserPhotosStreamIdxColumnValue>> c =
                new Callable<Multimap<UserPhotosStreamIdxRow, UserPhotosStreamIdxColumnValue>>() {
            @Override
            public Multimap<UserPhotosStreamIdxRow, UserPhotosStreamIdxColumnValue> call() {
                return get(cells);
            }
        };
        return AsyncProxy.create(exec.submit(c), Multimap.class);
    }

    @Override
    public List<UserPhotosStreamIdxColumnValue> getRowColumns(UserPhotosStreamIdxRow row) {
        return getRowColumns(row, ColumnSelection.all());
    }

    @Override
    public List<UserPhotosStreamIdxColumnValue> getRowColumns(UserPhotosStreamIdxRow row, ColumnSelection columns) {
        byte[] bytes = row.persistToBytes();
        RowResult<byte[]> rowResult = t.getRows(tableName, ImmutableSet.of(bytes), columns).get(bytes);
        if (rowResult == null) {
            return ImmutableList.of();
        } else {
            List<UserPhotosStreamIdxColumnValue> ret = Lists.newArrayListWithCapacity(rowResult.getColumns().size());
            for (Entry<byte[], byte[]> e : rowResult.getColumns().entrySet()) {
                UserPhotosStreamIdxColumn col = UserPhotosStreamIdxColumn.BYTES_HYDRATOR.hydrateFromBytes(e.getKey());
                Long val = UserPhotosStreamIdxColumnValue.hydrateValue(e.getValue());
                ret.add(UserPhotosStreamIdxColumnValue.of(col, val));
            }
            return ret;
        }
    }

    @Override
    public Multimap<UserPhotosStreamIdxRow, UserPhotosStreamIdxColumnValue> getRowsMultimap(Iterable<UserPhotosStreamIdxRow> rows) {
        return getRowsMultimapInternal(rows, ColumnSelection.all());
    }

    @Override
    public Multimap<UserPhotosStreamIdxRow, UserPhotosStreamIdxColumnValue> getRowsMultimap(Iterable<UserPhotosStreamIdxRow> rows, ColumnSelection columns) {
        return getRowsMultimapInternal(rows, columns);
    }

    @Override
    public Multimap<UserPhotosStreamIdxRow, UserPhotosStreamIdxColumnValue> getAsyncRowsMultimap(Iterable<UserPhotosStreamIdxRow> rows, ExecutorService exec) {
        return getAsyncRowsMultimap(rows, ColumnSelection.all(), exec);
    }

    @Override
    public Multimap<UserPhotosStreamIdxRow, UserPhotosStreamIdxColumnValue> getAsyncRowsMultimap(final Iterable<UserPhotosStreamIdxRow> rows, final ColumnSelection columns, ExecutorService exec) {
        Callable<Multimap<UserPhotosStreamIdxRow, UserPhotosStreamIdxColumnValue>> c =
                new Callable<Multimap<UserPhotosStreamIdxRow, UserPhotosStreamIdxColumnValue>>() {
            @Override
            public Multimap<UserPhotosStreamIdxRow, UserPhotosStreamIdxColumnValue> call() {
                return getRowsMultimapInternal(rows, columns);
            }
        };
        return AsyncProxy.create(exec.submit(c), Multimap.class);
    }

    private Multimap<UserPhotosStreamIdxRow, UserPhotosStreamIdxColumnValue> getRowsMultimapInternal(Iterable<UserPhotosStreamIdxRow> rows, ColumnSelection columns) {
        SortedMap<byte[], RowResult<byte[]>> results = t.getRows(tableName, Persistables.persistAll(rows), columns);
        return getRowMapFromRowResults(results.values());
    }

    private static Multimap<UserPhotosStreamIdxRow, UserPhotosStreamIdxColumnValue> getRowMapFromRowResults(Collection<RowResult<byte[]>> rowResults) {
        Multimap<UserPhotosStreamIdxRow, UserPhotosStreamIdxColumnValue> rowMap = HashMultimap.create();
        for (RowResult<byte[]> result : rowResults) {
            UserPhotosStreamIdxRow row = UserPhotosStreamIdxRow.BYTES_HYDRATOR.hydrateFromBytes(result.getRowName());
            for (Entry<byte[], byte[]> e : result.getColumns().entrySet()) {
                UserPhotosStreamIdxColumn col = UserPhotosStreamIdxColumn.BYTES_HYDRATOR.hydrateFromBytes(e.getKey());
                Long val = UserPhotosStreamIdxColumnValue.hydrateValue(e.getValue());
                rowMap.put(row, UserPhotosStreamIdxColumnValue.of(col, val));
            }
        }
        return rowMap;
    }

    public BatchingVisitableView<UserPhotosStreamIdxRowResult> getAllRowsUnordered() {
        return getAllRowsUnordered(ColumnSelection.all());
    }

    public BatchingVisitableView<UserPhotosStreamIdxRowResult> getAllRowsUnordered(ColumnSelection columns) {
        return BatchingVisitables.transform(t.getRange(tableName, RangeRequest.builder().retainColumns(columns).build()),
                new Function<RowResult<byte[]>, UserPhotosStreamIdxRowResult>() {
            @Override
            public UserPhotosStreamIdxRowResult apply(RowResult<byte[]> input) {
                return UserPhotosStreamIdxRowResult.of(input);
            }
        });
    }

    @Override
    public List<String> findConstraintFailures(Map<Cell, byte[]> writes,
                                               ConstraintCheckingTransaction transaction,
                                               AtlasDbConstraintCheckingMode constraintCheckingMode) {
        return ImmutableList.of();
    }

    @Override
    public List<String> findConstraintFailuresNoRead(Map<Cell, byte[]> writes,
                                                     AtlasDbConstraintCheckingMode constraintCheckingMode) {
        return ImmutableList.of();
    }

    /**
     * This exists to avoid unused import warnings
     * {@link AbortingVisitor}
     * {@link AbortingVisitors}
     * {@link ArrayListMultimap}
     * {@link Arrays}
     * {@link AssertUtils}
     * {@link AsyncProxy}
     * {@link AtlasDbConstraintCheckingMode}
     * {@link AtlasDbDynamicMutableExpiringTable}
     * {@link AtlasDbDynamicMutablePersistentTable}
     * {@link AtlasDbMutableExpiringTable}
     * {@link AtlasDbMutablePersistentTable}
     * {@link AtlasDbNamedExpiringSet}
     * {@link AtlasDbNamedMutableTable}
     * {@link AtlasDbNamedPersistentSet}
     * {@link BatchingVisitable}
     * {@link BatchingVisitableView}
     * {@link BatchingVisitables}
     * {@link Bytes}
     * {@link Callable}
     * {@link Cell}
     * {@link Cells}
     * {@link Collection}
     * {@link Collections2}
     * {@link ColumnSelection}
     * {@link ColumnValue}
     * {@link ColumnValues}
     * {@link ComparisonChain}
     * {@link Compression}
     * {@link CompressionUtils}
     * {@link ConstraintCheckingTransaction}
     * {@link Descending}
     * {@link EncodingUtils}
     * {@link Entry}
     * {@link EnumSet}
     * {@link ExecutorService}
     * {@link Function}
     * {@link HashMultimap}
     * {@link HashSet}
     * {@link Hydrator}
     * {@link ImmutableList}
     * {@link ImmutableMap}
     * {@link ImmutableMultimap}
     * {@link ImmutableSet}
     * {@link InvalidProtocolBufferException}
     * {@link IterableView}
     * {@link Iterables}
     * {@link Iterator}
     * {@link Joiner}
     * {@link List}
     * {@link Lists}
     * {@link Map}
     * {@link Maps}
     * {@link MoreObjects}
     * {@link Multimap}
     * {@link Multimaps}
     * {@link NamedColumnValue}
     * {@link Objects}
     * {@link Optional}
     * {@link Persistable}
     * {@link Persistables}
     * {@link Prefix}
     * {@link PtBytes}
     * {@link RangeRequest}
     * {@link RowResult}
     * {@link Set}
     * {@link Sets}
     * {@link Sha256Hash}
     * {@link SortedMap}
     * {@link Supplier}
     * {@link Throwables}
     * {@link TimeUnit}
     * {@link Transaction}
     * {@link TypedRowResult}
     * {@link UnsignedBytes}
     */
    static String __CLASS_HASH = "7rAxdKVw4tNsNCfgZ1LHBg==";
}