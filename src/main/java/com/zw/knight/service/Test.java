package com.zw.knight.service;

import com.zw.knight.util.GsonUtils;
import com.zw.knight.util.HttpClient;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * TODO:DOCUMENT ME!
 *
 * @author zw
 * @date 2020/7/23
 */
public class Test {
    private static final String URL = "http://10.100.11.13:8080/dj_vc/gold/coin/getRecordList?page=1&size=3000&user=";

    public static void main(String[] args) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(new File("C:\\Users\\zy-user\\Desktop\\User")));
            BufferedWriter bw = new BufferedWriter(new FileWriter(new File("C:\\Users\\zy-user\\Desktop\\UserCount")));
            Set<String> set = new HashSet<>();
            String line = "";
            while ((line = br.readLine()) != null) {
                String user = line.split("award\\?user=")[1].split("&")[0];
                set.add(user);
            }
            System.out.println(GsonUtils.toJson(set));
            Map<String, String> userMap = new HashMap<>();
            for (String user : set) {
                String result = HttpClient.get(URL + user);
                int count = result.split("红包奖励").length - 1;
                if (StringUtils.isNotBlank(result)) {
                    if (count < 10) {
                        userMap.put(user, count + "");
                    } else {
                        bw.write(user + "共领取过" + count + "次");
                        bw.newLine();
                    }
                }
            }
            for (Map.Entry<String, String> entry : userMap.entrySet()) {
                bw.write(entry.getKey() + "共领取过" + entry.getValue() + "次");
                bw.newLine();
            }
            bw.close();
            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
