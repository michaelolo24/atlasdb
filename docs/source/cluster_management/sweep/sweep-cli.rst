.. _atlas_sweep_cli:

AtlasDB Sweep Cli
=================

If you ever need to force a particular table or namespace to be swept immediately, you can run the CLI ``./bin/atlasdb sweep``, which has the following arguments:

.. csv-table::
   :header: "Short option", "Long option", "Description"
   :widths: 20, 40, 200

   ``-a``, ``--all``, "Sweep all tables, in all AtlasDB namespaces."
   ``-n``, ``--namespace <namespace name>``, "A namespace name to sweep, for instance ``-n product``"
   ``-t``, ``--table <table name>``, "A fully qualified table name to sweep. For example, to sweep the accounts table in the bank namespace, you would use ``-t bank.accounts``."
   ``-r``, ``--row <row name>``, "A row name encoded in hexadecimal to start sweeping from. The CLI prints out row names as it runs, so you can use this to easily resume a manual sweep job without unnecessarily processing rows that have already been recently swept. If this option is omitted, sweeping will process all rows of the table."

You must specify exactly one of ``-a``, ``-n``, and ``-t``. If you are sweeping a specific table with `-t`, you may additionally specify the start row with ``-r``. This is useful for resuming failed jobs.

Additionally, see the list of :ref:`tunable sweep parameters <sweep_tunable_parameters>` if the defaults are not working well for your AtlasDB instance.

Be aware that manual sweeping will ignore all conditions that factor into determining whether background sweepers should run, and that the background sweeper will also be affected by system property changes.

