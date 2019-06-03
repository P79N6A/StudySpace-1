package com.kanadem.XML

import scala.xml.{Elem, XML}

/**
  * @author KanadeM 2019/4/9
  */

object XMLTest {
  def main(args: Array[String]): Unit = {
    val xml: Elem = XML.loadString("<?xml version=\"1.0\" encoding=\"UTF-8\"?><ceb:CEB621Message xmlns:ceb=\"http://www.chinaport.gov.cn/ceb\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"><ceb:Inventory><ceb:InventoryHead><ceb:guid>521547eb-EDED-46B1-946C-BGT8NHI00011</ceb:guid><ceb:appType>2</ceb:appType><ceb:appTime>20190516150800</ceb:appTime><ceb:appStatus>2</ceb:appStatus><ceb:orderNo>order20190516150800002</ceb:orderNo><ceb:ebpCode>420196206P</ceb:ebpCode><ceb:ebpName>武汉汉蒙和商贸有限公司</ceb:ebpName><ceb:ebcCode>420196206P</ceb:ebcCode><ceb:ebcName>武汉汉蒙和商贸有限公司</ceb:ebcName><ceb:logisticsNo> 9700555708701 </ceb:logisticsNo><ceb:logisticsCode>4201680002</ceb:logisticsCode><ceb:logisticsName>武汉市邮政速递有限公司</ceb:logisticsName><ceb:copNo>M000857215JP3JW</ceb:copNo><ceb:preNo>M000857215JP3JW</ceb:preNo><ceb:assureCode>420196206P</ceb:assureCode><ceb:emsNo /><ceb:invtNo>M000857215JP3JW2</ceb:invtNo><ceb:ieFlag>I</ceb:ieFlag><ceb:declTime>20190111</ceb:declTime><ceb:customsCode>4722</ceb:customsCode><ceb:portCode>4722</ceb:portCode><ceb:ieDate>20190111</ceb:ieDate><ceb:buyerIdType>1</ceb:buyerIdType><ceb:buyerIdNumber>440301198509210811</ceb:buyerIdNumber><ceb:buyerName>李安东</ceb:buyerName><ceb:buyerTelephone>18629571826</ceb:buyerTelephone><ceb:consigneeAddress>北京北京市海淀区北京北京市海淀区黑山扈甲17号解放军第309医院</ceb:consigneeAddress><ceb:agentCode>420196206P</ceb:agentCode><ceb:agentName>武汉汉蒙和商贸有限公司</ceb:agentName><ceb:areaCode /><ceb:areaName /><ceb:tradeMode>9610</ceb:tradeMode><ceb:trafMode>5</ceb:trafMode><ceb:trafNo>CA926</ceb:trafNo><ceb:voyageNo>CA926</ceb:voyageNo><ceb:billNo>999-53458543</ceb:billNo><ceb:loctNo /><ceb:licenseNo /><ceb:country>116</ceb:country><ceb:freight>0</ceb:freight><ceb:insuredFee>0</ceb:insuredFee><ceb:currency>142</ceb:currency><ceb:packNo>1</ceb:packNo><ceb:grossWeight>4</ceb:grossWeight><ceb:netWeight>3.60</ceb:netWeight><ceb:note /></ceb:InventoryHead><ceb:InventoryList><ceb:gnum>1</ceb:gnum><ceb:itemRecordNo /><ceb:itemNo>201812000323</ceb:itemNo><ceb:itemName>AD-SD 香薰灯（muji）</ceb:itemName><ceb:gcode>7018900000</ceb:gcode><ceb:gname>AD-SD 香薰灯（muji）</ceb:gname><ceb:gmodel>AD-SD</ceb:gmodel><ceb:barCode /><ceb:country>116</ceb:country><ceb:tradeCountry>116</ceb:tradeCountry><ceb:currency>142</ceb:currency><ceb:qty>4</ceb:qty><ceb:unit>140</ceb:unit><ceb:qty1>4</ceb:qty1><ceb:unit1>035</ceb:unit1><ceb:price>45</ceb:price><ceb:totalPrice>180</ceb:totalPrice><ceb:note /></ceb:InventoryList></ceb:Inventory><ceb:BaseTransfer><ceb:copCode>420196206P</ceb:copCode><ceb:copName>汉汉蒙和商贸有限公司</ceb:copName><ceb:dxpMode>DXP</ceb:dxpMode><ceb:dxpId>EXP2016522002580001</ceb:dxpId><ceb:note>test</ceb:note></ceb:BaseTransfer></ceb:CEB621Message>")
    val headerField = (xml\"Inventory"\"InventoryHead").map(_\"ebcName")
    val mess = xml\\"CEB621Message"
    println(headerField(0).text)
    println(mess)
  }
}
