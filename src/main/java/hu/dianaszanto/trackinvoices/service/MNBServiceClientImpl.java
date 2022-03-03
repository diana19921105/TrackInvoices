package hu.dianaszanto.trackinvoices.service;

import hu.dianaszanto.trackinvoices.client.GetExchangeRatesRequestBody;
import hu.dianaszanto.trackinvoices.client.GetExchangeRatesResponseBody;
import hu.dianaszanto.trackinvoices.client.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Service;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.time.LocalDate;

@Service
public class MNBServiceClientImpl extends WebServiceGatewaySupport implements MNBServiceClient {
    @Autowired
    public MNBServiceClientImpl(Jaxb2Marshaller marshaller) {
        this.setMarshaller(marshaller);
        this.setUnmarshaller(marshaller);
        this.setDefaultUri("http://www.mnb.hu/arfolyamok.asmx");
    }

    @Override
    public GetExchangeRatesResponseBody getResponse() {
        return (GetExchangeRatesResponseBody) getWebServiceTemplate().marshalSendAndReceive(createRequestBody());
    }


    public GetExchangeRatesRequestBody createRequestBody() {
        ObjectFactory factory = new ObjectFactory();
        GetExchangeRatesRequestBody getExchangeRatesRequestBody = factory.createGetExchangeRatesRequestBody();
        JAXBElement<String> now = factory.createString(LocalDate.now().toString());
        JAXBElement<String> eur = factory.createString("EUR");
        getExchangeRatesRequestBody.setStartDate(now);
        getExchangeRatesRequestBody.setEndDate(now);
        getExchangeRatesRequestBody.setCurrencyNames(eur);

        jaxbObjectToXML(getExchangeRatesRequestBody);
        return getExchangeRatesRequestBody;
    }

    private static void jaxbObjectToXML(GetExchangeRatesRequestBody obj)
    {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(GetExchangeRatesRequestBody.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

            // To format XML
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);


            //If we have JAXB annotated class
            jaxbMarshaller.marshal(obj, System.out);

        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
}
