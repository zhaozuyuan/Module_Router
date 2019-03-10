package com.module_router.compiler;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * create by zuyuan on 2019/3/10
 */
public class RouterFileUtil {
    public static String ROUTER_TABLE_DIRECTORY = ".\\app\\router_table";

    public static void saveTable(List<Node> nodes) {
        if (nodes.size() == 0) return;
        File directory = new File(ROUTER_TABLE_DIRECTORY);
        directory.mkdirs();

        //router_app.txt
        String filePath = ROUTER_TABLE_DIRECTORY +"\\router_" + nodes.get(0).getModule() + ".txt";
        File file = new File(filePath);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                return;
            }
        }

        StringBuilder tableStr = new StringBuilder();
        tableStr.append("    ------------Router Table----------\n\n");
        for (Node n : nodes) {
            tableStr.append(" " + n.getDesc() + "\t");
            tableStr.append("Scheme: " + "router" + "\t");
            tableStr.append("Module: " + n.getModule() + "\t");
            tableStr.append("Name: " + n.getName() + "\n");
            tableStr.append(" Uri: " + n.getUrl() + "\n\n");
        }
        tableStr.append("\n");

        BufferedWriter writer = null;
        try {
            //true: don't delete old file
            writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(filePath, false), "UTF-8"));
            writer.write(tableStr.toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
