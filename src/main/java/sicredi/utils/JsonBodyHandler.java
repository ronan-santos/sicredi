package sicredi.utils;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodySubscriber;
import java.net.http.HttpResponse.ResponseInfo;
import java.nio.charset.StandardCharsets;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonBodyHandler<W> implements HttpResponse.BodyHandler<W> {

	private Class<W> classe;
	
	public JsonBodyHandler( Class<W> classe ) {
		
		this.classe = classe;
	}
	
	@Override
	public BodySubscriber<W> apply(ResponseInfo responseInfo) {
		
		return asJSON(classe);
	}
	
	public static <T> HttpResponse.BodySubscriber<T> asJSON(Class<T> targetType) {
        HttpResponse.BodySubscriber<String> upstream = HttpResponse.BodySubscribers.ofString(StandardCharsets.UTF_8);

        return HttpResponse.BodySubscribers.mapping(
                upstream,
                (String body) -> {
                    try {
                        ObjectMapper objectMapper = new ObjectMapper();
                        return objectMapper.readValue(body, targetType);
                    } catch (IOException e) {
                        throw new UncheckedIOException(e);
                    }
                });
    }

}
