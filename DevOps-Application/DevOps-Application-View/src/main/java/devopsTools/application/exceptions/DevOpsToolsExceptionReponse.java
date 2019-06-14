package devopsTools.application.exceptions;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DevOpsToolsExceptionReponse {
	private final Date timestamp;
	private final String message;
	private final String details;
}
