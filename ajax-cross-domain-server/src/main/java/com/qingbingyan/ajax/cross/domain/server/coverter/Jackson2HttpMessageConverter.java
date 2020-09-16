package com.qingbingyan.ajax.cross.domain.server.coverter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.qingbingyan.ajax.cross.domain.server.coverter.MyMappingJacksonValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.Charset;

/**
 * @author bear
 */
@Component("jackson2HttpMessageConverter")
public class Jackson2HttpMessageConverter extends MappingJackson2HttpMessageConverter {
 
  private Logger logger = LoggerFactory.getLogger(getClass());
  @Nullable
  private String jsonPrefix;
 
  @Override
  protected void writeInternal(Object object, Type type, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
    if (object instanceof String) {
      //绕开实际上返回的String类型，不序列化
      Charset charset = this.getDefaultCharset();
      StreamUtils.copy((String) object, charset, outputMessage.getBody());
    }else {
      super.writeInternal(object, type, outputMessage);
    }
  }

  /**
   * Specify a custom prefix to use for this view's JSON output.
   * Default is none.
   * @see #setPrefixJson
   */
  @Override
  public void setJsonPrefix(String jsonPrefix) {
    this.jsonPrefix = jsonPrefix;
  }

  /**
   * Indicate whether the JSON output by this view should be prefixed with ")]}', ". Default is false.
   * <p>Prefixing the JSON string in this manner is used to help prevent JSON Hijacking.
   * The prefix renders the string syntactically invalid as a script so that it cannot be hijacked.
   * This prefix should be stripped before parsing the string as JSON.
   * @see #setJsonPrefix
   */
  @Override
  public void setPrefixJson(boolean prefixJson) {
    this.jsonPrefix = (prefixJson ? ")]}', " : null);
  }
  @Override
  protected void writePrefix(JsonGenerator generator, Object object) throws IOException {
    if (this.jsonPrefix != null) {
      generator.writeRaw(this.jsonPrefix);
    }
    String jsonpFunction =
            (object instanceof MyMappingJacksonValue ? ((MyMappingJacksonValue) object).getJsonpFunction() : null);
    if (jsonpFunction != null) {
      generator.writeRaw("/**/");
      generator.writeRaw(jsonpFunction + "(");
    }
  }

  @Override
  protected void writeSuffix(JsonGenerator generator, Object object) throws IOException {
    String jsonpFunction =
            (object instanceof MyMappingJacksonValue ? ((MyMappingJacksonValue) object).getJsonpFunction() : null);
    if (jsonpFunction != null) {
      generator.writeRaw(");");
    }
  }


}