package com.medizine.backend.log;


import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {

  private static final Logger log = LogManager.getLogger(UncaughtExceptionHandler.class);

  @Override
  public void uncaughtException(Thread thread, Throwable throwable) {
    ObjectNode logEventJsonObjNode = JsonNodeFactory.instance.objectNode();

    if (throwable.getStackTrace() != null && throwable.getStackTrace().length > 0) {
      ArrayNode logStacktraceJsonArrNode = JsonNodeFactory.instance.arrayNode();

      for (StackTraceElement stackTraceElement : throwable.getStackTrace()) {
        logStacktraceJsonArrNode.add(stackTraceElement.toString());
      }

      logEventJsonObjNode.set("stacktrace", logStacktraceJsonArrNode);
    }

    logEventJsonObjNode.put("cause", throwable.toString());

    log.error(logEventJsonObjNode.toString(), throwable);
  }
}
