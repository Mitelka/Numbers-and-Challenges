package logic.JAXBcreation;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;

/**
 * Created by erez on 05/12/2016.
 */
public class Read_xml {

        public static GameDescriptor getInformation(String fileName)throws JAXBException {
            GameDescriptor myGameDescriptor = null;
            try {
                JAXBContext jaxbContext = JAXBContext.newInstance(GameDescriptor.class);
                Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
                File file = new File(fileName);
                Object obj = jaxbUnmarshaller.unmarshal(file);
                myGameDescriptor = (GameDescriptor) obj;

            } catch (JAXBException e) {
                e.printStackTrace();
            }

            return myGameDescriptor;
    }

}
