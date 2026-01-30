package Infrastructure;

import Infrastructure.Tools.CMDColor;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.io.File;

public class AppException extends Exception {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public AppException(String showMsg) {
        super((showMsg == null || showMsg.isBlank()) ? AppConfig.MSG_DEFAULT_ERROR : showMsg);
        saveLogFile(null, null, null);
    }

    public AppException(String showMsg, Exception e, Class<?> clase, String metodo) {
        super((showMsg == null || showMsg.isBlank()) ? AppConfig.MSG_DEFAULT_ERROR : showMsg);
        saveLogFile(e == null ? null : e.getMessage(), clase, metodo);
    }

    private void saveLogFile(String logMsg, Class<?> clase, String metodo) {
        String timestamp = LocalDateTime.now().format(FORMATTER);
        String className = (clase == null) ? AppConfig.MSG_DEFAULT_CLASS : clase.getSimpleName();
        String methodName = (metodo == null) ? AppConfig.MSG_DEFAULT_METHOD : metodo;
        String detailedLog = (logMsg == null || logMsg.isBlank()) ? AppConfig.MSG_DEFAULT_ERROR : logMsg;

        // Formato: primera línea SHOW con mensaje detallado, segunda línea LOG con
        // timestamp y clase.método
        String formattedLog = String.format("╭── SHOW ❱❱ %s : %s.%s \n╰──── LOG  ❱❱ %s | %s.%s | %s",
                detailedLog, className, methodName, timestamp, className, methodName, getMessage());

        try {
            File logFile = new File(AppConfig.LOGFILE);
            File parentDir = logFile.getParentFile();

            if (parentDir != null && !parentDir.exists()) {
                parentDir.mkdirs(); // CREA Storage\Logs
            }

            try (PrintWriter writer = new PrintWriter(new FileWriter(logFile, true))) {
                System.err.println(CMDColor.BLUE + formattedLog);
                writer.println(formattedLog);
            }
        } catch (Exception e) {
            System.err.println(CMDColor.RED + "[AppException.saveLogFile] ❱ " + e.getMessage());
        } finally {
            System.out.println(CMDColor.RESET);
        }
    }

    /**
     * Método estático para registrar errores de validación de UI sin lanzar
     * excepción
     * 
     * @param validationMessage Mensaje de validación que se mostrará al usuario
     * @param clase             Clase donde ocurrió la validación
     * @param metodo            Método donde ocurrió la validación
     */
    public static void logValidationError(String validationMessage, Class<?> clase, String metodo) {
        String timestamp = LocalDateTime.now().format(FORMATTER);
        String className = (clase == null) ? "UI" : clase.getSimpleName();
        String methodName = (metodo == null) ? "validation" : metodo;
        String message = (validationMessage == null || validationMessage.isBlank())
                ? "Error de validación"
                : validationMessage;

        String formattedLog = String.format("╭── SHOW ❱❱ Validación fallida : %s.%s \n╰──── LOG  ❱❱ %s | %s.%s | %s",
                className, methodName, timestamp, className, methodName, message);

        try {
            File logFile = new File(AppConfig.LOGFILE);
            File parentDir = logFile.getParentFile();

            if (parentDir != null && !parentDir.exists()) {
                parentDir.mkdirs();
            }

            try (PrintWriter writer = new PrintWriter(new FileWriter(logFile, true))) {
                System.err.println(CMDColor.YELLOW + formattedLog);
                writer.println(formattedLog);
            }
        } catch (Exception e) {
            System.err.println(CMDColor.RED + "[AppException.logValidationError] ❱ " + e.getMessage());
        } finally {
            System.out.println(CMDColor.RESET);
        }
    }
}