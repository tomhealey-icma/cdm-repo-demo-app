package com.finxis.cdm.crossproductapp.util;


import com.finxis.cdm.crossproductapp.*;
import cdm.base.staticdata.party.Party;
import cdm.legaldocumentation.contract.processor.PartyMappingProcessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Injector;
import com.regnosys.rosetta.common.hashing.GlobalKeyProcessStep;
import com.regnosys.rosetta.common.hashing.NonNullHashCollector;
import com.regnosys.rosetta.common.serialisation.RosettaObjectMapper;
import com.regnosys.rosetta.common.translation.Mapping;
import com.regnosys.rosetta.common.translation.Path;
import com.rosetta.model.lib.RosettaModelObject;
import com.rosetta.model.lib.RosettaModelObjectBuilder;
import com.rosetta.model.lib.annotations.RosettaSynonym;
import com.rosetta.model.lib.process.PostProcessStep;
import com.rosetta.model.metafields.FieldWithMetaDate;
import javassist.ClassMap;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import cdm.event.common.TradeState;
import cdm.product.asset.InterestRatePayout;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.Resources;
import com.regnosys.rosetta.common.serialisation.RosettaObjectMapper;
import com.regnosys.rosetta.common.util.ClassPathUtils;
import com.rosetta.model.lib.records.Date;
import com.rosetta.model.lib.process.PostProcessStep;




public class XmlReader {


	static Injector injector;
	Map<String, String> classMap = new HashMap<>();
	//List<Party> party = new java.util.ArrayList<>(List.of(Party.builder()));
	java.util.ArrayList<Party> party = new ArrayList<>();

	private final PostProcessStep keyProcessor;

	public XmlReader() {
		keyProcessor = new GlobalKeyProcessStep(NonNullHashCollector::new);
	}

	private <T extends RosettaModelObject> T addGlobalKey(Class<T> type, T modelObject) {
		RosettaModelObjectBuilder builder = modelObject.toBuilder();
		keyProcessor.runProcessStep(type, builder);
		return type.cast(builder.build());
	}
	public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException, ClassNotFoundException, InvocationTargetException, IllegalAccessException, NoSuchMethodException, InstantiationException {

		XmlReader xmlReader = new XmlReader();
		//File xmlFile = new File("rosetta-source/src/main/resources/repo-xml/xml/repo-execution.xml");

		File xmlFile = new File("../workflow/pairoff/xml/icma-repo-execution.xml");

		Document doc = xmlReader.readXmlFile(xmlFile);
		//NamedNodeMap nmap = xmlReader.getElement(doc);

		String result = xmlReader.createNewTradeInstruction(doc);

		List <Party> party = xmlReader.getPartyElements(doc);
		//RosettaModelObject o = xmlReader.mapXmlToCDMObject(doc);



		RepoLifeCycle repoExecution = new RepoLifeCycle();

		System.out.println("Processing XML File Complete.");

	}

	public String createNewTradeInstruction(Document doc){

		FieldWithMetaDate transactionDate = createTradeDate(doc);
		//String purchaseDate = createPurchaseDate(doc);


		return "True";

	}


	public String getTradeDateString (Document doc){

		NodeList ch = doc.getChildNodes();
		NodeList td = doc.getElementsByTagName("TransactionDate");
		Node nl0 = td.item(0);

		IcmaRepoUtil ru = new IcmaRepoUtil();

		String tradeDateStr = nl0.getFirstChild().getNodeValue().toString();
		tradeDateStr = tradeDateStr.replaceAll("\\s", "") + "T00:00:00.000+00:00";


		return tradeDateStr;

	}
	public FieldWithMetaDate createTradeDate(Document doc){

		NodeList ch = doc.getChildNodes();
		NodeList td = doc.getElementsByTagName("TransactionDate");
		Node nl0 = td.item(0);

		IcmaRepoUtil ru = new IcmaRepoUtil();

		String tradeDateStr = nl0.getFirstChild().getNodeValue().toString();
		tradeDateStr = tradeDateStr.replaceAll("\\s", "") + "T00:00:00.000+00:00";

		DateTimeFormatter formatter  = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSz");
		ZonedDateTime zdtWithZoneOffset = ZonedDateTime.parse(tradeDateStr, formatter);
		ZonedDateTime zdtInLocalTimeline = zdtWithZoneOffset.withZoneSameInstant(ZoneId.systemDefault());

		FieldWithMetaDate tradeDate = addGlobalKey(FieldWithMetaDate.class,
				ru.createTradeDate(zdtWithZoneOffset.getYear(), zdtWithZoneOffset.getMonthValue(), zdtWithZoneOffset.getDayOfMonth()));


		return tradeDate;
	}
public Document  readXmlFile(File xmlFile) throws IOException, SAXException, ParserConfigurationException {
	
	DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
	Document doc = builder.parse(xmlFile);

	doc.getDocumentElement().normalize();
	return doc;
}

public NamedNodeMap getElement(Document doc) {
	Node first = doc.getElementsByTagName("WorkflowStepName").item(0);
	NamedNodeMap attrList = first.getAttributes();

	String workflowName = first.getFirstChild().getNodeValue().toString();



	switch (workflowName) {

		case "NewTrade":

			String businessEventJson = createNewTradeBusinessEvent(doc);

			break;
		default:

	}

	return attrList;
}


public String createNewTradeBusinessEvent(Document doc){

		Node purchaseDateNode = doc.getElementsByTagName("PurchaseDate").item(0);
		String purchaseDate = purchaseDateNode.getChildNodes().item(0).getNodeValue().trim().toString();

		Node repurchaseDateNode = doc.getElementsByTagName("RepurchaseDate").item(0);
		String repurchaseDate = purchaseDateNode.getChildNodes().item(0).getNodeValue().trim().toString();


		RepoLifeCycle repoExecution = new RepoLifeCycle();
		//String businessEvent = repoExecution.RepoExecution();


	return "True";
}


public List <Party> getPartyElements(Node nl) throws IOException, ClassNotFoundException, InvocationTargetException, IllegalAccessException, NoSuchMethodException, InstantiationException {

		NodeList ch = nl.getChildNodes();
		RosettaModelObject obj = null;


		for (int i =0; i< ch.getLength(); i++) {
			getPartyElements(ch.item(i));
			//obj = this.mapXmlToCDMObject(ch.item(i).getNodeName().toString());
			if (ch.item(i).getNodeName().toString() == "Party"){
				party.add(this.buildPartyObject(ch.item(i)));
			}
			System.out.println(ch.item(i).getNodeName().toString());
		}
		return party;
	}

public RosettaModelObject mapXmlToCDMObject(String nodeName) throws IOException, ClassNotFoundException, InvocationTargetException, IllegalAccessException, NoSuchMethodException, InstantiationException {

		//1. Get node name
		//2. Map node name to CDM name space and type
		//3. Create CDM object
		//4. Return CDM Object
		//5. Execute CDM Workflow and Event

		CdmUtil cdmUtil = new CdmUtil();

		//CdmClassMap map = new CdmClassMap();
		//map.buildClassMap(classMap);

		String classReference = classMap.get(nodeName);
		Class<?> clazz = Class.forName(classReference);
		Method method = clazz.getMethod("builder");
		RosettaModelObject o = (RosettaModelObject) method.invoke(null);
		o.build();

		//assertNotNull(o);
		//assertTrue(o instanceof Party);

		return o;


}

	public Party buildPartyObject(Node node){

		Party party = Party.builder()
				.setNameValue(this.getPartyName(node))
				.build();

		return party;

	}

	public String getPartyName(Node nl) {

		String pname = "";
		NodeList ch = nl.getChildNodes();

		for (int i = 0; i < ch.getLength(); i++) {
			this.getPartyName(ch.item(i));
			//obj = this.mapXmlToCDMObject(ch.item(i).getNodeName().toString());
			if (ch.item(i).getNodeName().toString() == "PartyName") {
				pname = ch.item(i).getFirstChild().getNodeValue();
			}
		}
		return pname;
	}

	private List<Mapping> getXmlMappings(Path payerXmlPath, String payerXmlValue, Path receiverXmlPath, String receiverXmlValue) {
		return Arrays.asList(
				new Mapping(payerXmlPath,
						payerXmlValue,
						null,
						null,
						"no destination",
						false,
						false,
						false),
				new Mapping(receiverXmlPath,
						receiverXmlValue,
						null,
						null,
						"no destination",
						false,
						false,
						false)
		);
	}

}
