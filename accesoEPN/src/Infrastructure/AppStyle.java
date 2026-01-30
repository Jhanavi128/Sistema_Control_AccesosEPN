package Infrastructure;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.SwingConstants;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public abstract class AppStyle {
    private static final String FONT_FAMILY      = "NovaMono";
    
    // Configuración de Colores
    public static final Color COLOR_FONT         = new Color(220, 0, 0); 
    public static final Color COLOR_FONT_LIGHT   = new Color(100, 100, 100);
    public static final Color COLOR_CURSOR       = Color.black;
    public static final Color COLOR_BORDER       = Color.lightGray;

    // Configuración de Fuentes
    public static final Font  FONT               = new Font(FONT_FAMILY, Font.PLAIN, 14);
    public static final Font  FONT_SMALL         = new Font(FONT_FAMILY, Font.PLAIN, 10);
    public static final Font  FONT_BOLD          = new Font(FONT_FAMILY, Font.BOLD | Font.PLAIN, 15);

    // Alineaciones Swing
    public static final int ALIGNMENT_LEFT   = SwingConstants.LEFT;
    public static final int ALIGNMENT_RIGHT  = SwingConstants.RIGHT;
    public static final int ALIGNMENT_CENTER = SwingConstants.CENTER;

    // Cursores
    public static final Cursor CURSOR_HAND    = new Cursor(Cursor.HAND_CURSOR);
    public static final Cursor CURSOR_DEFAULT = new Cursor(Cursor.DEFAULT_CURSOR);

    private AppStyle() {}

    /**
     * Crea un borde compuesto con una línea gris claro y un margen interno (padding).
     * @return CompoundBorder para componentes Swing.
     */
    public static final CompoundBorder createBorderRect(){
        return BorderFactory.createCompoundBorder(new LineBorder(Color.lightGray),
                                                    new EmptyBorder(5, 5, 5, 5));
    }
}