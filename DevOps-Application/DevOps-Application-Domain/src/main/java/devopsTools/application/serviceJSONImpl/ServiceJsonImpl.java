package devopsTools.application.serviceJSONImpl;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class ServiceJsonImpl {

	public ServiceJsonImpl() {
		// TODO Auto-generated constructor stub
	}

	protected <T> List<Object> getAllFromJsonFile(String filename, TypeReference<List<T>> typeReference) {
		List<Object> allObjs = null;
		String jsonFilename = "/json/" + filename;
		log.trace("Loading '" + this.getClass() + "' from Json file: " + jsonFilename);
		ObjectMapper mapper = new ObjectMapper();
		InputStream inputStream = TypeReference.class.getResourceAsStream(jsonFilename);
		try {
			allObjs = mapper.readValue(inputStream, typeReference);
			if (log.isTraceEnabled()) {
				for (Object obj : allObjs) {
					log.trace("Line Item: " + obj);
				}
			}
		} catch (IOException e) {
			log.warn("Unable to load JSON file: " + jsonFilename + "\n Error Message: " + e.getMessage());
		}
		return allObjs;
	}
}
