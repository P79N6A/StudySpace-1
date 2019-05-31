package com.kanadem.Demo.Calculate;

import java.util.Properties;
import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

/**
 * @author KanadeM 2019/4/5
 */

public class ProducerDemo {

  public static void main(String[] args) throws InterruptedException {
    Properties props = new Properties();
    props.put("metadata.broker.list", "192.168.0.114:9092");
    props.put("serializer.class", "kafka.serializer.StringEncoder");
    ProducerConfig config = new ProducerConfig(props);
    Producer<String, String> producer = new Producer<String, String>(config);
    for(int i = 0; i < 10001; i++){
      System.out.println("SENDED");
      producer.send(new KeyedMessage<String, String>("test",
          "<?xml version=\"1.0\" encoding=\"UTF-8\"?><ceb:CEB511Message guid=\"4CDE1CFD-EDED-46B1-946C-B8022E42FC94\" version=\"1.0\"  xmlns:ceb=\"http://www.chinaport.gov.cn/ceb\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"><ceb:Logistics><ceb:LogisticsHead><ceb:guid>23B7E38C-04CE-4D77-8F77-EF2ED1573F40</ceb:guid><ceb:appType>1</ceb:appType><ceb:appTime>20180810173525</ceb:appTime><ceb:appStatus>2</ceb:appStatus><ceb:logisticsCode>3120980105</ceb:logisticsCode><ceb:logisticsName>上海韵达货运有限公司</ceb:logisticsName><ceb:logisticsNo>7700073791399</ceb:logisticsNo><ceb:billNo></ceb:billNo><ceb:freight>0.0</ceb:freight><ceb:insuredFee>0</ceb:insuredFee><ceb:currency>142</ceb:currency><ceb:weight>1.98</ceb:weight><ceb:packNo>1</ceb:packNo><ceb:goodsInfo></ceb:goodsInfo><ceb:consignee>左平</ceb:consignee><ceb:consigneeAddress><![CDATA[重庆重庆市九龙坡区 市  杨家坪街道杨家坪西郊路24巷22号附43号(400051)]]></ceb:consigneeAddress><ceb:consigneeTelephone>13617660519</ceb:consigneeTelephone><ceb:note></ceb:note></ceb:LogisticsHead></ceb:Logistics><ceb:BaseTransfer><ceb:copCode>3120980105</ceb:copCode><ceb:copName>上海韵达货运有限公司</ceb:copName><ceb:dxpMode>DXP</ceb:dxpMode><ceb:dxpId>DXPENT0000012698</ceb:dxpId><ceb:note></ceb:note></ceb:BaseTransfer></ceb:CEB511Message>"));

    }
    for(int i = 199; i < 10001; i++){
      System.out.println("SENDED");
           producer.send(new KeyedMessage<String, String>("test",
          "<?xml version=\"1.0\" encoding=\"UTF-8\"?><ceb:CEB513Message guid=\"4CDE1CFD-EDED-46B1-946C-B8022E42FC94\" version=\"1.0\"  xmlns:ceb=\"http://www.chinaport.gov.cn/ceb\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"><ceb:Logistics><ceb:LogisticsHead><ceb:guid>23B7E38C-04CE-4D77-8F77-EF2ED1573F40</ceb:guid><ceb:appType>1</ceb:appType><ceb:appTime>20180810173525</ceb:appTime><ceb:appStatus>2</ceb:appStatus><ceb:logisticsCode>3120980105</ceb:logisticsCode><ceb:logisticsName>上海韵达货运有限公司</ceb:logisticsName><ceb:logisticsNo>7700073791399</ceb:logisticsNo><ceb:billNo></ceb:billNo><ceb:freight>0.0</ceb:freight><ceb:insuredFee>0</ceb:insuredFee><ceb:currency>142</ceb:currency><ceb:weight>1.98</ceb:weight><ceb:packNo>1</ceb:packNo><ceb:goodsInfo></ceb:goodsInfo><ceb:consignee>左平</ceb:consignee><ceb:consigneeAddress><![CDATA[重庆重庆市九龙坡区 市  杨家坪街道杨家坪西郊路24巷22号附43号(400051)]]></ceb:consigneeAddress><ceb:consigneeTelephone>13617660519</ceb:consigneeTelephone><ceb:note></ceb:note></ceb:LogisticsHead></ceb:Logistics><ceb:BaseTransfer><ceb:copCode>3120980105</ceb:copCode><ceb:copName>上海韵达货运有限公司</ceb:copName><ceb:dxpMode>DXP</ceb:dxpMode><ceb:dxpId>DXPENT0000012698</ceb:dxpId><ceb:note></ceb:note></ceb:BaseTransfer></ceb:CEB511Message>"));

    }
    }

}
