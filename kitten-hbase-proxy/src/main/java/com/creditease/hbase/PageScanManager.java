package com.creditease.hbase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * author: GaoYS
 * CreateData: 2015/7/28 18:36
 */
public class PageScanManager {

    private static final Logger logger = LoggerFactory.getLogger(PageScanManager.class);
    private final static long expirtTime = 10 * 60 * 1000;
    private Map<String, PageScan> scanMaps = new ConcurrentHashMap<String, PageScan>();
    private static PageScanManager instance = new PageScanManager();

    private PageScanManager() {

        Thread monitor = new Thread(new ScanMonitor(),
                "pagescan-monitor-thread");
        monitor.setDaemon(true);
        monitor.start();
    }

    public static PageScanManager getInstance() {
        return instance;
    }

    public PageScan getPageScan(String scanStartKey, String scanEndKey,
                                int pageSize) {
        String scanKey = scanStartKey + scanEndKey + pageSize;
        PageScan ps = scanMaps.get(scanKey);
        if (ps != null)
            return ps;
        synchronized (scanMaps) {
            if (scanMaps.get(scanKey) == null) {
                PageScan nps = new PageScan(scanStartKey, scanEndKey, pageSize);
                scanMaps.put(nps.getKEY(), nps);
            }
        }
        return scanMaps.get(scanKey);
    }

    /**
     * 内部监控PageScan的线程，如果某个PageScan在指定的时间内没有被访问 。则销毁
     */
    private class ScanMonitor implements Runnable {

        public void run() {
            while (true) {
                long now = System.currentTimeMillis();
                Iterator<PageScan> it = scanMaps.values().iterator();
                while (it.hasNext()) {
                    PageScan pS = it.next();
                    if ((now - expirtTime) > pS.getStartTime()) {
                        String k = pS.getKEY();
                        scanMaps.remove(k);
                        logger.info(pS.geteKey() + " removed");
                    }
                }
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }
    }
}
