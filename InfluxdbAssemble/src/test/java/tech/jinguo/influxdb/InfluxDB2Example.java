/*
 *
 * @Project: knowledge
 * @File: InfluxDB2Example
 * @Package: tech.jinguo.kafka.demo
 * @Date: 2021/11/10 11:22
 * @Author: by jinguo.qian
 *
 */
package tech.jinguo.influxdb;
import java.time.Instant;
import java.util.List;

import com.influxdb.annotations.Column;
import com.influxdb.annotations.Measurement;
import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
import com.influxdb.client.QueryApi;
import com.influxdb.client.WriteApiBlocking;
import com.influxdb.client.domain.WritePrecision;
import com.influxdb.client.write.Point;
import com.influxdb.query.FluxRecord;
import com.influxdb.query.FluxTable;

/**
 * @author jinguo.qian
 * @title: InfluxDB2Example
 * @projectName knowledge
 * @description: 测试
 * @date 2021/11/10 11:22
 */


public class InfluxDB2Example {

    private static char[] token = "7It4y2dnb9ZHICUp21Ez8qgpbzvpEaRKBxw5hdO2PeTxpj__JTtd37ASUpA-fgS9Qk4oNZalx7Yf0gGDefWZVQ==".toCharArray();
    private static String org = "knowlege";
    private static String bucket = "dbtest";

    public static void main(final String[] args) {

        InfluxDBClient influxDBClient = InfluxDBClientFactory.create("http://127.0.0.1:8086", token, org, bucket);

        //
        // Write data
        //
        WriteApiBlocking writeApi = influxDBClient.getWriteApiBlocking();

        //
        // Write by Data Point
        //
        Point point = Point.measurement("temperature")
                .addTag("location", "west")
                .addField("value", 55D)
                .time(Instant.now().toEpochMilli(), WritePrecision.MS);

        writeApi.writePoint(point);

        //
        // Write by LineProtocol
        //
        writeApi.writeRecord(WritePrecision.NS, "temperature,location=north value=60.0");

        //
        // Write by POJO
        //
        Temperature temperature = new Temperature();
        temperature.location = "south";
        temperature.value = 62D;
        temperature.time = Instant.now();

        writeApi.writeMeasurement( WritePrecision.NS, temperature);

        //
        // Query data
        //
        String flux = "from(bucket:\"dbtest\") |> range(start: 0)";

        QueryApi queryApi = influxDBClient.getQueryApi();

        List<FluxTable> tables = queryApi.query(flux);
        for (FluxTable fluxTable : tables) {
            List<FluxRecord> records = fluxTable.getRecords();
            for (FluxRecord fluxRecord : records) {
                System.out.println(fluxRecord.getTime() + ": " + fluxRecord.getValueByKey("_value"));
            }
        }

        influxDBClient.close();
    }

    @Measurement(name = "temperature")
    private static class Temperature {

        @Column(tag = true)
        String location;

        @Column
        Double value;

        @Column(timestamp = true)
        Instant time;
    }
}