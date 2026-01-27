package BusinessLogic.Entities;

import DataAccess.DTOs.UsuarioDTO;
import Infrastructure.AppException;
import BusinessLogic.FactoryBL;
import DataAccess.DAOs.UsuarioDAO;
import java.util.HashMap;
import java.util.Map;

public class BLLogin {
    private final FactoryBL<UsuarioDTO> factory;

    public BLLogin() throws AppException {
        factory = new FactoryBL<>(UsuarioDAO.class);
    }

    public Map<String, Object> login(String codigo, String password) throws AppException {
        Map<String, Object> result = new HashMap<>();
        
        // 1. Obtener todos los usuarios activos (Estado = 'A')
        var usuarios = factory.getAll();
        
        System.out.println("--- INTENTO DE LOGIN ---");
        System.out.println("Buscando Código: [" + codigo + "]");
        System.out.println("Usuarios encontrados en BD: " + usuarios.size());

        for (UsuarioDTO u : usuarios) {
            // DEBUG: Ver qué está comparando el servidor
            System.out.println("Comparando con usuario en BD: [" + u.getCodigoUnico() + "] Rol: " + u.getRol());

            // 2. Comparación de Código Único y Contraseña
            // Usamos .trim() para evitar errores por espacios en blanco accidentales
            if (u.getCodigoUnico().trim().equals(codigo.trim()) && 
                u.getContrasena().equals(password)) {
                
                System.out.println("✅ ¡COINCIDENCIA ENCONTRADA!");
                
                result.put("success", true);
                result.put("userCode", u.getCodigoUnico());
                result.put("idUsuario", u.getIdUsuario());

                // Redirección basada en ROL
                String rol = u.getRol().trim();
                if (rol.equalsIgnoreCase("Estudiante")) {
                    result.put("redirect", "/student.html");
                } else if (rol.equalsIgnoreCase("Guardia")) {
                    result.put("redirect", "/scanner.html");
                } else {
                    result.put("redirect", "/index.html");
                }
                
                return result;
            }
        }

        System.out.println("❌ No se encontró coincidencia.");
        result.put("success", false);
        return result;
    }
}