cd /D %~dp0
java -jar C:\ApplicationData\jwsdp-1.5\jaxb\lib\jaxb-xjc.jar -d . -b PCR_Difference.binding.xjb.xml PCR_DifferenceXMLSchema.xsd PCR_Difference.xsd
PAUSE