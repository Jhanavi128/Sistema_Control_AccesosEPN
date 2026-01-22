//  © 2K26 ❱──💀──❰ pat_mic ? code is life : life is code
package Infrastructure;

import javax.swing.JOptionPane;

public abstract class AppMSG {
    private AppMSG() {}

    /**
     * Muestra un mensaje de información estándar.
     */
    public static final void show(String msg){
        JOptionPane.showMessageDialog(null, msg, "🐜 AntCiberDron", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Muestra un mensaje de error.
     */
    public static final void showError(String msg){
        JOptionPane.showMessageDialog(null, msg, "🐜 AntCiberDron", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Muestra un cuadro de diálogo de confirmación (Sí/No).
     * @return true si el usuario selecciona "Sí".
     */
    public static final boolean showConfirmYesNo(String msg){
        return (JOptionPane.showConfirmDialog(null, msg, "🐜 AntCiberDron", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION);
    }
}