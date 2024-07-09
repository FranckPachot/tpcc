package com.oltpbenchmark.schema;

import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.oltpbenchmark.DBWorkload;
import com.oltpbenchmark.benchmarks.tpcc.TPCCConstants;

public class TPCCTableSchemas {
    public static Map<String, TableSchema> tables;
    public static boolean schemaUpdated = false;
    public static void updateTableSchema(String dbType){
        if(!schemaUpdated) {
            tables = Collections.unmodifiableMap(Stream.of(
                    new TableSchemaBuilder(TPCCConstants.TABLENAME_ORDERLINE)
                            .column("ol_w_id", "int ")
                            .column("ol_d_id", "int ")
                            .column("ol_o_id", "int ")
                            .column("ol_number", "int ")
                            .column("ol_i_id", "int ")
                            .column("ol_delivery_d", "timestamp NULL DEFAULT NULL")
                            .column("ol_amount", "decimal(6,2) ")
                            .column("ol_supply_w_id", "int ")
                            .column("ol_quantity", "decimal(2,0) ")
                            .column("ol_dist_info", "char(24) ")
                            .primaryKey(dbType.equals("yugabyte") ? "((ol_w_id,ol_d_id) HASH, ol_o_id,ol_number)" : "(ol_w_id,ol_d_id,ol_o_id,ol_number)")
                            .partitionKey("(ol_w_id)")
                            .build(),
                    new TableSchemaBuilder(TPCCConstants.TABLENAME_NEWORDER)
                            .column("no_w_id", "int ")
                            .column("no_d_id", "int ")
                            .column("no_o_id", "int ")
                            .primaryKey(dbType.equals("yugabyte") ? "((no_w_id,no_d_id) HASH,no_o_id)" : "(no_w_id,no_d_id,no_o_id)")
                            .partitionKey("(no_w_id)")
                            .build(),
                    new TableSchemaBuilder(TPCCConstants.TABLENAME_STOCK)
                            .column("s_w_id", "int ")
                            .column("s_i_id", "int ")
                            .column("s_quantity", "decimal(4,0) ")
                            .column("s_ytd", "decimal(8,2) ")
                            .column("s_order_cnt", "int ")
                            .column("s_remote_cnt", "int ")
                            .column("s_data", "varchar(50) ")
                            .column("s_dist_01", "char(24) ")
                            .column("s_dist_02", "char(24) ")
                            .column("s_dist_03", "char(24) ")
                            .column("s_dist_04", "char(24) ")
                            .column("s_dist_05", "char(24) ")
                            .column("s_dist_06", "char(24) ")
                            .column("s_dist_07", "char(24) ")
                            .column("s_dist_08", "char(24) ")
                            .column("s_dist_09", "char(24) ")
                            .column("s_dist_10", "char(24) ")
                            .primaryKey(dbType.equals("yugabyte") ? "(s_w_id HASH, s_i_id ASC)" : "(s_w_id,s_i_id)")
                            .partitionKey("(s_w_id)")
                            .build(),
                    new TableSchemaBuilder(TPCCConstants.TABLENAME_OPENORDER)
                            .column("o_w_id", "int ")
                            .column("o_d_id", "int ")
                            .column("o_id", "int ")
                            .column("o_c_id", "int ")
                            .column("o_carrier_id", "int DEFAULT NULL")
                            .column("o_ol_cnt", "decimal(2,0) ")
                            .column("o_all_local", "decimal(1,0) ")
                            .column("o_entry_d", "timestamp  DEFAULT CURRENT_TIMESTAMP")
                            .primaryKey(dbType.equals("yugabyte") ? "((o_w_id,o_d_id) HASH,o_id)" : "(o_w_id,o_d_id,o_id)")
                            .partitionKey("(o_w_id)")
                            .build(),
                    new TableSchemaBuilder(TPCCConstants.TABLENAME_HISTORY)
                            .column("h_c_id", "int ")
                            .column("h_c_d_id", "int ")
                            .column("h_c_w_id", "int ")
                            .column("h_d_id", "int ")
                            .column("h_w_id", "int ")
                            .column("h_date", "timestamp  DEFAULT CURRENT_TIMESTAMP")
                            .column("h_amount", "decimal(6,2) ")
                            .column("h_data", "varchar(24) ")
                            .partitionKey("(h_w_id)")
                            .build(),
                    new TableSchemaBuilder(TPCCConstants.TABLENAME_CUSTOMER)
                            .column("c_w_id", "int ")
                            .column("c_d_id", "int ")
                            .column("c_id", "int ")
                            .column("c_discount", "decimal(4,4) ")
                            .column("c_credit", "char(2) ")
                            .column("c_last", "varchar(16) ")
                            .column("c_first", "varchar(16) ")
                            .column("c_credit_lim", "decimal(12,2) ")
                            .column("c_balance", "decimal(12,2) ")
                            .column("c_ytd_payment", "float ")
                            .column("c_payment_cnt", "int ")
                            .column("c_delivery_cnt", "int ")
                            .column("c_street_1", "varchar(20) ")
                            .column("c_street_2", "varchar(20) ")
                            .column("c_city", "varchar(20) ")
                            .column("c_state", "char(2) ")
                            .column("c_zip", "char(9) ")
                            .column("c_phone", "char(16) ")
                            .column("c_since", "timestamp  DEFAULT CURRENT_TIMESTAMP")
                            .column("c_middle", "char(2) ")
                            .column("c_data", "varchar(500) ")
                            .primaryKey(dbType.equals("yugabyte") ? "((c_w_id,c_d_id) HASH,c_id)" : "(c_w_id,c_d_id,c_id)")
                            .partitionKey("(c_w_id)")
                            .build(),
                    new TableSchemaBuilder(TPCCConstants.TABLENAME_DISTRICT)
                            .column("d_w_id", "int ")
                            .column("d_id", "int ")
                            .column("d_ytd", "decimal(12,2) ")
                            .column("d_tax", "decimal(4,4) ")
                            .column("d_next_o_id", "int ")
                            .column("d_name", "varchar(10) ")
                            .column("d_street_1", "varchar(20) ")
                            .column("d_street_2", "varchar(20) ")
                            .column("d_city", "varchar(20) ")
                            .column("d_state", "char(2) ")
                            .column("d_zip", "char(9) ")
                            .primaryKey(dbType.equals("yugabyte") ? "((d_w_id,d_id) HASH)" : "(d_w_id,d_id)")
                            .partitionKey("(d_w_id)")
                            .build(),
                    new TableSchemaBuilder(TPCCConstants.TABLENAME_ITEM)
                            .column("i_id", "int ")
                            .column("i_name", "varchar(24) ")
                            .column("i_price", "decimal(5,2) ")
                            .column("i_data", "varchar(50) ")
                            .column("i_im_id", "int ")
                            .primaryKey("(i_id)")
                            .build(),
                    new TableSchemaBuilder(TPCCConstants.TABLENAME_WAREHOUSE)
                            .column("w_id", "int ")
                            .column("w_ytd", "decimal(12,2) ")
                            .column("w_tax", "decimal(4,4) ")
                            .column("w_name", "varchar(10) ")
                            .column("w_street_1", "varchar(20) ")
                            .column("w_street_2", "varchar(20) ")
                            .column("w_city", "varchar(20) ")
                            .column("w_state", "char(2) ")
                            .column("w_zip", "char(9) ")
                            .primaryKey("(w_id)")
                            .partitionKey("(w_id)")
                            .build()
            ).collect(Collectors.toMap(TableSchema::name, e -> e)));
            schemaUpdated = true;
        }
    }
    public static TableSchema getTableSchema(String tablename) {
        updateTableSchema(DBWorkload.dbtype);
        return tables.get(tablename);
    }
}
