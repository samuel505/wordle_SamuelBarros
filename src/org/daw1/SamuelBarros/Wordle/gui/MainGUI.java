/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.daw1.SamuelBarros.Wordle.gui;

import java.awt.Color;
import java.io.IOException;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import org.daw1.SamuelBarros.Wordle.clases.GestorMotorArchivo;
import org.daw1.SamuelBarros.Wordle.clases.GestorMotorBaseDeDatos;
import org.daw1.SamuelBarros.Wordle.clases.MotorTest;
import org.daw1.SamuelBarros.Wordle.clases.iMotor;

/**
 *
 * @author alumno
 */
public class MainGUI extends javax.swing.JFrame {

    private static String idioma = "idioma";

    protected static String tipoMotor = "motor";
    private String palabraAleatoria = "";
    private iMotor gm = null;

    private static final Color COLOR_ROJO = new Color(255, 0, 0);
    private static final Color COLOR_AMARILLO = new Color(255, 255, 0);
    private static final Color COLOR_VERDE = new Color(0, 255, 0);

    private static final int MAX_INTENTOS = 6;
    private static final int TAMANHO_PALABRA = 5;

    private final JLabel labels[][] = new JLabel[MAX_INTENTOS][TAMANHO_PALABRA];

    protected iMotor selectMotor() {
        iMotor g = null;
        if (this.archivojRadioButtonMenuItem.isSelected()) {
            g = new GestorMotorArchivo(idioma);
            // System.out.println("Motor archivo");

        } else if (this.baseDeDatosjRadioButtonMenuItem.isSelected()) {
            g = new GestorMotorBaseDeDatos(idioma);
            // System.out.println("Motor base de datos");

        } else if (this.testjRadioButtonMenuItem.isSelected()) {
            g = new MotorTest();
            //System.out.println("Motor test");

        }

        return g;
    }

    private void testFilas() {

        for (int i = 0; i < labels.length; i++) {
            JLabel[] label = labels[i];
            for (int j = 0; j < label.length; j++) {
                JLabel jLabel = label[j];
                jLabel.setVisible(false);
                //jLabel.setForeground(COLOR_ROJO);

            }
        }
    }

    private void ocultarFilas() {
        finaljLabel.setVisible(false);
        for (int i = 0; i < labels.length; i++) {
            JLabel[] label = labels[i];
            for (int j = 0; j < label.length; j++) {
                JLabel jLabel = label[j];
                jLabel.setVisible(false);
            }
        }
    }

    private void testCambiarLetra(int fila, int letra) {

        JLabel[] label = labels[fila];
        for (int j = 0; j < label.length; j++) {
            JLabel l = label[letra];
            l.setVisible(true);
            //l.setForeground(COLOR_ROJO);

        }
    }

    private void testCambiarPalabra(int fila, char[] p) {

        JLabel[] label = labels[fila];
        for (int j = 0; j < label.length; j++) {
            JLabel l = label[j];
            l.setVisible(true);
            l.setText(p[j] + "");
        }
    }

    private final void IniciarLabels() {
        for (int i = 1; i <= MAX_INTENTOS; i++) {
            for (int j = 1; j <= TAMANHO_PALABRA; j++) {
                try {
                    String palabraLabel = "jLabel" + i + "_" + j;
                    JLabel aux = (JLabel) this.getClass().getDeclaredField(palabraLabel).get(this);
                    labels[i - 1][j - 1] = aux;

                } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException ex) {
                    Logger.getLogger(MainGUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

    }

//**************************************************************************
//(arreglar amarillo, bucle dowhile, y crear clases?)
    int intento = 1;
    Set<String> letrasV = new TreeSet<>();
    Set<String> letrasA = new TreeSet<>();
    Set<String> letrasR = new TreeSet<>();

    private void ganar(char[] persona, char[] aleatorio) {

        JLabel[] label = labels[intento - 1];
        for (int j = 0; j < label.length; j++) {
            JLabel l = label[j];
            l.setVisible(true);
            l.setForeground(COLOR_VERDE);
        }

        for (int j = 0; j < label.length; j++) {
            JLabel l = label[j];
            l.setVisible(true);
            if (persona[j] == aleatorio[j]) {
                l.setForeground(COLOR_VERDE);

                letrasV.add((persona[j] + "").toUpperCase());

                String sV = "";
                for (String lV : letrasV) {
                    sV += lV + " ";
                }

                bienjLabel.setText(sV);

            }

        }

        letrasA.removeAll(letrasV);
        String ssA = "";
        for (String lA : letrasA) {
            ssA += lA + " ";
        }

        existenjLabel.setText(ssA);
        finaljLabel.setText("Has ganado en " + intento + " intentos!!");
        finaljLabel.setForeground(COLOR_VERDE);
        finaljLabel.setVisible(true);
        enviarjButton.setEnabled(false);
        palabrasjTextField.setEnabled(false);
        testCambiarPalabra(intento - 1, persona);
        
    }

    private void existeLetras(String p) {
        //p="pablo";
        //String a ="samue";

        String a = palabraAleatoria;
        char persona[] = p.toUpperCase().toCharArray();

        if (a == null) {
            // System.out.println("NO HAY PALABRAS");
            JOptionPane.showMessageDialog(this, "El archivo de texto que estas usando no tiene palabras \n" + "Añadele palabras para poder empezar la partida", "ERROR", JOptionPane.ERROR_MESSAGE);
        } else {

            char aleatorio[] = a.toUpperCase().toCharArray();

            if (gm.existePalabra(p.toUpperCase())) {

                if (a.toUpperCase().equals(p.toUpperCase())) {
                   ganar(persona,aleatorio);

                } else {
                    testCambiarPalabra(intento - 1, persona);
                    JLabel[] label = labels[intento - 1];
                    for (int j = 0; j < label.length; j++) {
                        JLabel l = label[j];
                        l.setVisible(true);
                        if (persona[j] == aleatorio[j]) {
                            l.setForeground(COLOR_VERDE);

                            letrasV.add((persona[j] + "").toUpperCase());

                             if (letrasA.contains(persona[j] + "")) {
                                //System.out.println(persona[j] + "");
                                letrasA.removeAll(letrasV);
                            }

                            String sV = "";
                            for (String lV : letrasV) {
                                sV += lV + " ";
                            }

                            String ssA = "";
                            for (String lA : letrasA) {
                                ssA += lA + " ";
                            }

                            bienjLabel.setText(sV);
                            existenjLabel.setText(ssA); 

                        } else if (a.contains(persona[j] + "")) {
                            l.setForeground(COLOR_AMARILLO);

                            letrasA.add((persona[j] + "").toUpperCase());

                            if (letrasA.contains(persona[j] + "")) {
                                //System.out.println(persona[j] + "");
                                letrasA.removeAll(letrasV);
                            }

                            String sV = "";
                            for (String lV : letrasV) {
                                sV += lV + " ";
                            }

                            String ssA = "";
                            for (String lA : letrasA) {
                                ssA += lA + " ";
                            }

                            bienjLabel.setText(sV);
                            existenjLabel.setText(ssA); 
                        } else {
                            l.setForeground(COLOR_ROJO);

                            letrasR.add((persona[j] + "").toUpperCase());

                            String sR = "";
                            for (String lR : letrasR) {
                                sR += lR + " ";
                            }
                            maljLabel.setText(sR);
                        }
                        //l.setForeground(COLOR_VERDE);
                    }
                    if (intento < 6) {
                        intento++;
                        
                        //System.out.println("intento nº " + intento);
                    } else {
                        finaljLabel.setForeground(COLOR_ROJO);
                        finaljLabel.setText("Game over, has perdido");
                        finaljLabel.setVisible(true);
                        enviarjButton.setEnabled(false);
                        palabrasjTextField.setEnabled(false);
                        JOptionPane.showMessageDialog(this, "La palabra correcta era: " + palabraAleatoria, "Fin de partida", JOptionPane.INFORMATION_MESSAGE);

                    }
                }
            } else {
                errorjLabel.setText("Texto invalido");
            }
        }
    }

    private final void IniciarPartida() throws IOException {
        intento = 1;
        initComponents();
        IniciarLabels();
        ocultarFilas();

    }
//--------------------------------------------------//

    public MainGUI() {
        try {

            IniciarPartida();
            idioma();
            gm = selectMotor();
            palabrasjTextField.setEnabled(false);
            enviarjButton.setEnabled(false);
            //testFilas();
            //testCambiarFila(0);
            //testCambiarLetra(0,0);
            //testCambiarPalabra(0, "");
        } catch (IOException ex) {
            Logger.getLogger(MainGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    protected void tipoMotor() {
        if (baseDeDatosjRadioButtonMenuItem.isSelected()) {
            tipoMotor = "base";

        } else if (archivojRadioButtonMenuItem.isSelected()) {
            tipoMotor = "archivo";
        } else if (testjRadioButtonMenuItem.isSelected()) {
            tipoMotor = "test";
        }

    }

    protected void idioma() {
        if (esjRadioButtonMenuItem.isSelected()) {
            idioma = "es";

        } else if (gljRadioButtonMenuItem.isSelected()) {
            idioma = "gl";
        }

    }

    private void nuevaPartida() {
        ocultarFilas();
        idioma();
        gm = selectMotor();
        tipoMotor();
        letrasV.clear();
        letrasA.clear();
        letrasR.clear();
        bienjLabel.setText("");
        existenjLabel.setText("");
        maljLabel.setText("");
        if (archivojRadioButtonMenuItem.isSelected()) {

            // System.out.println(idioma);
            if (esjRadioButtonMenuItem.isSelected()) {
                ocultarFilas();
                // System.out.println("Español seleccionado");
                intento = 1;
                enviarjButton.setEnabled(true);
                try {
                    gm.cargarTextos();
                } catch (IOException ex) {
                    System.out.println("no se pudo cargar los datos " + ex);
                }
                palabraAleatoria = gm.palabraAleatoria();
                //System.out.println(palabraAleatoria);

                //&&!jLabel1_2.getText().equalsIgnoreCase("a")&&!jLabel1_3.getText().equalsIgnoreCase("a")&&!jLabel1_4.getText().equalsIgnoreCase("a")&&!jLabel1_5.getText().equalsIgnoreCase("a")
                if (!jLabel1_1.getText().equalsIgnoreCase("a")) {
                    ocultarFilas();
                }
            } else if (gljRadioButtonMenuItem.isSelected()) {
                ocultarFilas();
                // System.out.println("Gallego seleccionado");
                intento = 1;
                enviarjButton.setEnabled(true);
                try {
                    gm.cargarTextos();
                } catch (IOException ex) {
                    System.out.println("no se pudo cargar los datos " + ex);
                }
                palabraAleatoria = gm.palabraAleatoria();
                System.out.println(palabraAleatoria);

                //&&!jLabel1_2.getText().equalsIgnoreCase("a")&&!jLabel1_3.getText().equalsIgnoreCase("a")&&!jLabel1_4.getText().equalsIgnoreCase("a")&&!jLabel1_5.getText().equalsIgnoreCase("a")
                

            }

        } else if (baseDeDatosjRadioButtonMenuItem.isSelected()) {

            if (esjRadioButtonMenuItem.isSelected()) {
                // System.out.println("Español seleccionado");
                intento = 1;
                enviarjButton.setEnabled(true);
                try {
                    gm.cargarTextos();
                } catch (IOException ex) {
                    System.out.println("no se pudo cargar los datos" + ex);
                }
                palabraAleatoria = gm.palabraAleatoria();
              //  System.out.println(palabraAleatoria);

                //&&!jLabel1_2.getText().equalsIgnoreCase("a")&&!jLabel1_3.getText().equalsIgnoreCase("a")&&!jLabel1_4.getText().equalsIgnoreCase("a")&&!jLabel1_5.getText().equalsIgnoreCase("a")
                
            } else if (gljRadioButtonMenuItem.isSelected()) {
               // System.out.println("Galego seleccionado");
                intento = 1;
                enviarjButton.setEnabled(true);
                try {
                    gm.cargarTextos();
                } catch (IOException ex) {
                    System.out.println("no se pudo cargar los datos" + ex);
                }
                palabraAleatoria = gm.palabraAleatoria();
             //   System.out.println(palabraAleatoria);

                //&&!jLabel1_2.getText().equalsIgnoreCase("a")&&!jLabel1_3.getText().equalsIgnoreCase("a")&&!jLabel1_4.getText().equalsIgnoreCase("a")&&!jLabel1_5.getText().equalsIgnoreCase("a")
                

            }
        } else if (testjRadioButtonMenuItem.isSelected()) {

            if (esjRadioButtonMenuItem.isSelected()) {
               // System.out.println("Español seleccionado");
                intento = 1;
                enviarjButton.setEnabled(true);
                try {
                    gm.cargarTextos();
                } catch (IOException ex) {
                    System.out.println("no se pudo cargar los datos" + ex);
                }
                palabraAleatoria = gm.palabraAleatoria();
              //  System.out.println(palabraAleatoria);

                //&&!jLabel1_2.getText().equalsIgnoreCase("a")&&!jLabel1_3.getText().equalsIgnoreCase("a")&&!jLabel1_4.getText().equalsIgnoreCase("a")&&!jLabel1_5.getText().equalsIgnoreCase("a")
                
            } else if (gljRadioButtonMenuItem.isSelected()) {
               // System.out.println("Galego seleccionado");
                intento = 1;
                enviarjButton.setEnabled(true);
                try {
                    gm.cargarTextos();
                } catch (IOException ex) {
                    System.out.println("no se pudo cargar los datos "+ ex);
                }
                palabraAleatoria = gm.palabraAleatoria();
                //System.out.println(palabraAleatoria);

                //&&!jLabel1_2.getText().equalsIgnoreCase("a")&&!jLabel1_3.getText().equalsIgnoreCase("a")&&!jLabel1_4.getText().equalsIgnoreCase("a")&&!jLabel1_5.getText().equalsIgnoreCase("a")
                

            }
        }

        palabrasjTextField.setEnabled(true);

    }

//**************************************************************************
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        idiomabuttonGroup = new javax.swing.ButtonGroup();
        motorbuttonGroup = new javax.swing.ButtonGroup();
        mainjPanel = new javax.swing.JPanel();
        letrasjPanel = new javax.swing.JPanel();
        jLabel1_1 = new javax.swing.JLabel();
        jLabel1_2 = new javax.swing.JLabel();
        jLabel1_3 = new javax.swing.JLabel();
        jLabel1_4 = new javax.swing.JLabel();
        jLabel1_5 = new javax.swing.JLabel();
        jLabel2_1 = new javax.swing.JLabel();
        jLabel2_2 = new javax.swing.JLabel();
        jLabel2_3 = new javax.swing.JLabel();
        jLabel2_4 = new javax.swing.JLabel();
        jLabel2_5 = new javax.swing.JLabel();
        jLabel3_1 = new javax.swing.JLabel();
        jLabel3_2 = new javax.swing.JLabel();
        jLabel3_3 = new javax.swing.JLabel();
        jLabel3_4 = new javax.swing.JLabel();
        jLabel3_5 = new javax.swing.JLabel();
        jLabel4_1 = new javax.swing.JLabel();
        jLabel4_2 = new javax.swing.JLabel();
        jLabel4_3 = new javax.swing.JLabel();
        jLabel4_4 = new javax.swing.JLabel();
        jLabel4_5 = new javax.swing.JLabel();
        jLabel5_1 = new javax.swing.JLabel();
        jLabel5_2 = new javax.swing.JLabel();
        jLabel5_3 = new javax.swing.JLabel();
        jLabel5_4 = new javax.swing.JLabel();
        jLabel5_5 = new javax.swing.JLabel();
        jLabel6_1 = new javax.swing.JLabel();
        jLabel6_2 = new javax.swing.JLabel();
        jLabel6_3 = new javax.swing.JLabel();
        jLabel6_4 = new javax.swing.JLabel();
        jLabel6_5 = new javax.swing.JLabel();
        bottomjPanel = new javax.swing.JPanel();
        estadojPanel = new javax.swing.JPanel();
        maljPanel = new javax.swing.JPanel();
        maljLabel = new javax.swing.JLabel();
        existenjPanel = new javax.swing.JPanel();
        existenjLabel = new javax.swing.JLabel();
        bienjPanel = new javax.swing.JPanel();
        bienjLabel = new javax.swing.JLabel();
        inputjPanel = new javax.swing.JPanel();
        palabrasjTextField = new javax.swing.JTextField();
        enviarjButton = new javax.swing.JButton();
        exitojPanel = new javax.swing.JPanel();
        finaljLabel = new javax.swing.JLabel();
        errorjPanel = new javax.swing.JPanel();
        errorjLabel = new javax.swing.JLabel();
        menujMenuBar = new javax.swing.JMenuBar();
        ArchivojMenu = new javax.swing.JMenu();
        nuevojMenuItem = new javax.swing.JMenuItem();
        salirjMenuItem = new javax.swing.JMenuItem();
        MotoresjMenu = new javax.swing.JMenu();
        ajustesjMenuItem = new javax.swing.JMenuItem();
        archivojRadioButtonMenuItem = new javax.swing.JRadioButtonMenuItem();
        baseDeDatosjRadioButtonMenuItem = new javax.swing.JRadioButtonMenuItem();
        testjRadioButtonMenuItem = new javax.swing.JRadioButtonMenuItem();
        idiomajMenu = new javax.swing.JMenu();
        esjRadioButtonMenuItem = new javax.swing.JRadioButtonMenuItem();
        gljRadioButtonMenuItem = new javax.swing.JRadioButtonMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("DAW1 Wordle SamuelBarros");
        setAlwaysOnTop(true);

        mainjPanel.setBackground(new java.awt.Color(255, 255, 255));
        mainjPanel.setLayout(new java.awt.BorderLayout());

        letrasjPanel.setBackground(new java.awt.Color(255, 255, 255));
        letrasjPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED, null, java.awt.Color.gray));
        letrasjPanel.setMinimumSize(new java.awt.Dimension(160, 365));
        letrasjPanel.setPreferredSize(new java.awt.Dimension(400, 228));
        letrasjPanel.setLayout(new java.awt.GridLayout(6, 5));

        jLabel1_1.setFont(new java.awt.Font("Dialog", 1, 48)); // NOI18N
        jLabel1_1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1_1.setText("A");
        jLabel1_1.setToolTipText("");
        letrasjPanel.add(jLabel1_1);

        jLabel1_2.setFont(new java.awt.Font("Dialog", 1, 48)); // NOI18N
        jLabel1_2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1_2.setText("A");
        jLabel1_2.setToolTipText("");
        letrasjPanel.add(jLabel1_2);

        jLabel1_3.setFont(new java.awt.Font("Dialog", 1, 48)); // NOI18N
        jLabel1_3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1_3.setText("A");
        jLabel1_3.setToolTipText("");
        letrasjPanel.add(jLabel1_3);

        jLabel1_4.setFont(new java.awt.Font("Dialog", 1, 48)); // NOI18N
        jLabel1_4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1_4.setText("A");
        jLabel1_4.setToolTipText("");
        letrasjPanel.add(jLabel1_4);

        jLabel1_5.setFont(new java.awt.Font("Dialog", 1, 48)); // NOI18N
        jLabel1_5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1_5.setText("A");
        jLabel1_5.setToolTipText("");
        letrasjPanel.add(jLabel1_5);

        jLabel2_1.setFont(new java.awt.Font("Dialog", 1, 48)); // NOI18N
        jLabel2_1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2_1.setText("A");
        jLabel2_1.setToolTipText("");
        letrasjPanel.add(jLabel2_1);

        jLabel2_2.setFont(new java.awt.Font("Dialog", 1, 48)); // NOI18N
        jLabel2_2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2_2.setText("A");
        jLabel2_2.setToolTipText("");
        letrasjPanel.add(jLabel2_2);

        jLabel2_3.setFont(new java.awt.Font("Dialog", 1, 48)); // NOI18N
        jLabel2_3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2_3.setText("A");
        jLabel2_3.setToolTipText("");
        letrasjPanel.add(jLabel2_3);

        jLabel2_4.setFont(new java.awt.Font("Dialog", 1, 48)); // NOI18N
        jLabel2_4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2_4.setText("A");
        jLabel2_4.setToolTipText("");
        letrasjPanel.add(jLabel2_4);

        jLabel2_5.setFont(new java.awt.Font("Dialog", 1, 48)); // NOI18N
        jLabel2_5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2_5.setText("A");
        jLabel2_5.setToolTipText("");
        letrasjPanel.add(jLabel2_5);

        jLabel3_1.setFont(new java.awt.Font("Dialog", 1, 48)); // NOI18N
        jLabel3_1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3_1.setText("A");
        jLabel3_1.setToolTipText("");
        letrasjPanel.add(jLabel3_1);

        jLabel3_2.setFont(new java.awt.Font("Dialog", 1, 48)); // NOI18N
        jLabel3_2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3_2.setText("A");
        jLabel3_2.setToolTipText("");
        letrasjPanel.add(jLabel3_2);

        jLabel3_3.setFont(new java.awt.Font("Dialog", 1, 48)); // NOI18N
        jLabel3_3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3_3.setText("A");
        jLabel3_3.setToolTipText("");
        letrasjPanel.add(jLabel3_3);

        jLabel3_4.setFont(new java.awt.Font("Dialog", 1, 48)); // NOI18N
        jLabel3_4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3_4.setText("A");
        jLabel3_4.setToolTipText("");
        letrasjPanel.add(jLabel3_4);

        jLabel3_5.setFont(new java.awt.Font("Dialog", 1, 48)); // NOI18N
        jLabel3_5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3_5.setText("A");
        jLabel3_5.setToolTipText("");
        letrasjPanel.add(jLabel3_5);

        jLabel4_1.setFont(new java.awt.Font("Dialog", 1, 48)); // NOI18N
        jLabel4_1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4_1.setText("A");
        jLabel4_1.setToolTipText("");
        letrasjPanel.add(jLabel4_1);

        jLabel4_2.setFont(new java.awt.Font("Dialog", 1, 48)); // NOI18N
        jLabel4_2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4_2.setText("A");
        jLabel4_2.setToolTipText("");
        letrasjPanel.add(jLabel4_2);

        jLabel4_3.setFont(new java.awt.Font("Dialog", 1, 48)); // NOI18N
        jLabel4_3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4_3.setText("A");
        jLabel4_3.setToolTipText("");
        letrasjPanel.add(jLabel4_3);

        jLabel4_4.setFont(new java.awt.Font("Dialog", 1, 48)); // NOI18N
        jLabel4_4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4_4.setText("A");
        jLabel4_4.setToolTipText("");
        letrasjPanel.add(jLabel4_4);

        jLabel4_5.setFont(new java.awt.Font("Dialog", 1, 48)); // NOI18N
        jLabel4_5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4_5.setText("A");
        jLabel4_5.setToolTipText("");
        letrasjPanel.add(jLabel4_5);

        jLabel5_1.setFont(new java.awt.Font("Dialog", 1, 48)); // NOI18N
        jLabel5_1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5_1.setText("A");
        jLabel5_1.setToolTipText("");
        letrasjPanel.add(jLabel5_1);

        jLabel5_2.setFont(new java.awt.Font("Dialog", 1, 48)); // NOI18N
        jLabel5_2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5_2.setText("A");
        jLabel5_2.setToolTipText("");
        letrasjPanel.add(jLabel5_2);

        jLabel5_3.setFont(new java.awt.Font("Dialog", 1, 48)); // NOI18N
        jLabel5_3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5_3.setText("A");
        jLabel5_3.setToolTipText("");
        letrasjPanel.add(jLabel5_3);

        jLabel5_4.setFont(new java.awt.Font("Dialog", 1, 48)); // NOI18N
        jLabel5_4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5_4.setText("A");
        jLabel5_4.setToolTipText("");
        letrasjPanel.add(jLabel5_4);

        jLabel5_5.setFont(new java.awt.Font("Dialog", 1, 48)); // NOI18N
        jLabel5_5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5_5.setText("A");
        jLabel5_5.setToolTipText("");
        letrasjPanel.add(jLabel5_5);

        jLabel6_1.setFont(new java.awt.Font("Dialog", 1, 48)); // NOI18N
        jLabel6_1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6_1.setText("A");
        jLabel6_1.setToolTipText("");
        letrasjPanel.add(jLabel6_1);

        jLabel6_2.setFont(new java.awt.Font("Dialog", 1, 48)); // NOI18N
        jLabel6_2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6_2.setText("A");
        jLabel6_2.setToolTipText("");
        letrasjPanel.add(jLabel6_2);

        jLabel6_3.setFont(new java.awt.Font("Dialog", 1, 48)); // NOI18N
        jLabel6_3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6_3.setText("A");
        jLabel6_3.setToolTipText("");
        letrasjPanel.add(jLabel6_3);

        jLabel6_4.setFont(new java.awt.Font("Dialog", 1, 48)); // NOI18N
        jLabel6_4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6_4.setText("A");
        jLabel6_4.setToolTipText("");
        letrasjPanel.add(jLabel6_4);

        jLabel6_5.setFont(new java.awt.Font("Dialog", 1, 48)); // NOI18N
        jLabel6_5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6_5.setText("A");
        jLabel6_5.setToolTipText("");
        letrasjPanel.add(jLabel6_5);

        mainjPanel.add(letrasjPanel, java.awt.BorderLayout.CENTER);

        bottomjPanel.setBackground(new java.awt.Color(255, 255, 255));
        bottomjPanel.setPreferredSize(new java.awt.Dimension(200, 125));
        bottomjPanel.setLayout(new java.awt.GridLayout(2, 2));

        estadojPanel.setBackground(new java.awt.Color(255, 255, 255));
        estadojPanel.setLayout(new java.awt.GridLayout(3, 0));

        maljPanel.setBackground(new java.awt.Color(255, 255, 255));
        maljPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        maljLabel.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        maljLabel.setForeground(new java.awt.Color(255, 0, 0));
        maljPanel.add(maljLabel);

        estadojPanel.add(maljPanel);

        existenjPanel.setBackground(new java.awt.Color(255, 255, 255));
        existenjPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        existenjLabel.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        existenjLabel.setForeground(new java.awt.Color(255, 255, 0));
        existenjPanel.add(existenjLabel);

        estadojPanel.add(existenjPanel);

        bienjPanel.setBackground(new java.awt.Color(255, 255, 255));
        bienjPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        bienjLabel.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        bienjLabel.setForeground(new java.awt.Color(0, 255, 0));
        bienjPanel.add(bienjLabel);

        estadojPanel.add(bienjPanel);

        bottomjPanel.add(estadojPanel);

        inputjPanel.setBackground(new java.awt.Color(255, 255, 255));

        palabrasjTextField.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        palabrasjTextField.setToolTipText("");
        palabrasjTextField.setPreferredSize(new java.awt.Dimension(160, 23));
        inputjPanel.add(palabrasjTextField);

        enviarjButton.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        enviarjButton.setText("Enviar");
        enviarjButton.setPreferredSize(new java.awt.Dimension(75, 25));
        enviarjButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                enviarjButtonActionPerformed(evt);
            }
        });
        inputjPanel.add(enviarjButton);

        bottomjPanel.add(inputjPanel);

        exitojPanel.setBackground(new java.awt.Color(255, 255, 255));
        exitojPanel.setLayout(new java.awt.GridBagLayout());

        finaljLabel.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        finaljLabel.setForeground(new java.awt.Color(0, 255, 0));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(24, 137, 24, 137);
        exitojPanel.add(finaljLabel, gridBagConstraints);

        bottomjPanel.add(exitojPanel);

        errorjPanel.setBackground(new java.awt.Color(255, 255, 255));
        errorjPanel.setLayout(new java.awt.GridBagLayout());

        errorjLabel.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        errorjLabel.setForeground(new java.awt.Color(255, 0, 0));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(24, 145, 24, 145);
        errorjPanel.add(errorjLabel, gridBagConstraints);

        bottomjPanel.add(errorjPanel);

        mainjPanel.add(bottomjPanel, java.awt.BorderLayout.PAGE_END);

        menujMenuBar.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N

        ArchivojMenu.setText("Archivo");

        nuevojMenuItem.setText("nueva partida");
        nuevojMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nuevojMenuItemActionPerformed(evt);
            }
        });
        ArchivojMenu.add(nuevojMenuItem);

        salirjMenuItem.setText("salir");
        salirjMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                salirjMenuItemActionPerformed(evt);
            }
        });
        ArchivojMenu.add(salirjMenuItem);

        menujMenuBar.add(ArchivojMenu);

        MotoresjMenu.setText("Motor");

        ajustesjMenuItem.setText("ajustes");
        ajustesjMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ajustesjMenuItemActionPerformed(evt);
            }
        });
        MotoresjMenu.add(ajustesjMenuItem);

        motorbuttonGroup.add(archivojRadioButtonMenuItem);
        archivojRadioButtonMenuItem.setSelected(true);
        archivojRadioButtonMenuItem.setText("archivo de texto");
        archivojRadioButtonMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                archivojRadioButtonMenuItemActionPerformed(evt);
            }
        });
        MotoresjMenu.add(archivojRadioButtonMenuItem);

        motorbuttonGroup.add(baseDeDatosjRadioButtonMenuItem);
        baseDeDatosjRadioButtonMenuItem.setText("base de datos");
        baseDeDatosjRadioButtonMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                baseDeDatosjRadioButtonMenuItemActionPerformed(evt);
            }
        });
        MotoresjMenu.add(baseDeDatosjRadioButtonMenuItem);

        motorbuttonGroup.add(testjRadioButtonMenuItem);
        testjRadioButtonMenuItem.setText("test");
        testjRadioButtonMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                testjRadioButtonMenuItemActionPerformed(evt);
            }
        });
        MotoresjMenu.add(testjRadioButtonMenuItem);

        menujMenuBar.add(MotoresjMenu);

        idiomajMenu.setText("idioma");

        idiomabuttonGroup.add(esjRadioButtonMenuItem);
        esjRadioButtonMenuItem.setSelected(true);
        esjRadioButtonMenuItem.setText("Español");
        esjRadioButtonMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                esjRadioButtonMenuItemActionPerformed(evt);
            }
        });
        idiomajMenu.add(esjRadioButtonMenuItem);

        idiomabuttonGroup.add(gljRadioButtonMenuItem);
        gljRadioButtonMenuItem.setText("Galego");
        gljRadioButtonMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gljRadioButtonMenuItemActionPerformed(evt);
            }
        });
        idiomajMenu.add(gljRadioButtonMenuItem);

        menujMenuBar.add(idiomajMenu);

        setJMenuBar(menujMenuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainjPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 943, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainjPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 643, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void esjRadioButtonMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_esjRadioButtonMenuItemActionPerformed
        nuevaPartida();
    }//GEN-LAST:event_esjRadioButtonMenuItemActionPerformed

    private void gljRadioButtonMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_gljRadioButtonMenuItemActionPerformed
        nuevaPartida();
    }//GEN-LAST:event_gljRadioButtonMenuItemActionPerformed

    private void enviarjButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_enviarjButtonActionPerformed
        errorjLabel.setText("");
        if (palabrasjTextField.getText().matches("[A-Za-z]{5}")) {
            existeLetras(this.palabrasjTextField.getText().toUpperCase());
        } else {
            errorjLabel.setText("La palabra debe ser de 5 letras");
            errorjLabel.setForeground(COLOR_ROJO);
        }

        //existeLetras(usuario");
    }//GEN-LAST:event_enviarjButtonActionPerformed

    private void nuevojMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nuevojMenuItemActionPerformed
        nuevaPartida();

    }//GEN-LAST:event_nuevojMenuItemActionPerformed

    private void salirjMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_salirjMenuItemActionPerformed
        System.exit(WIDTH);
    }//GEN-LAST:event_salirjMenuItemActionPerformed

    private void ajustesjMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ajustesjMenuItemActionPerformed
        nuevaPartida();
        MotorGUI dialog = new MotorGUI(this, true, idioma);
        dialog.setVisible(true);
        nuevaPartida();
    }//GEN-LAST:event_ajustesjMenuItemActionPerformed

    private void archivojRadioButtonMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_archivojRadioButtonMenuItemActionPerformed
        nuevaPartida();

    }//GEN-LAST:event_archivojRadioButtonMenuItemActionPerformed

    private void baseDeDatosjRadioButtonMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_baseDeDatosjRadioButtonMenuItemActionPerformed
        nuevaPartida();

    }//GEN-LAST:event_baseDeDatosjRadioButtonMenuItemActionPerformed

    private void testjRadioButtonMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_testjRadioButtonMenuItemActionPerformed
        nuevaPartida();
    }//GEN-LAST:event_testjRadioButtonMenuItemActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainGUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu ArchivojMenu;
    private javax.swing.JMenu MotoresjMenu;
    private javax.swing.JMenuItem ajustesjMenuItem;
    private javax.swing.JRadioButtonMenuItem archivojRadioButtonMenuItem;
    private javax.swing.JRadioButtonMenuItem baseDeDatosjRadioButtonMenuItem;
    private javax.swing.JLabel bienjLabel;
    private javax.swing.JPanel bienjPanel;
    private javax.swing.JPanel bottomjPanel;
    private javax.swing.JButton enviarjButton;
    private javax.swing.JLabel errorjLabel;
    private javax.swing.JPanel errorjPanel;
    private javax.swing.JRadioButtonMenuItem esjRadioButtonMenuItem;
    private javax.swing.JPanel estadojPanel;
    private javax.swing.JLabel existenjLabel;
    private javax.swing.JPanel existenjPanel;
    private javax.swing.JPanel exitojPanel;
    private javax.swing.JLabel finaljLabel;
    private javax.swing.JRadioButtonMenuItem gljRadioButtonMenuItem;
    private javax.swing.ButtonGroup idiomabuttonGroup;
    private javax.swing.JMenu idiomajMenu;
    private javax.swing.JPanel inputjPanel;
    private javax.swing.JLabel jLabel1_1;
    private javax.swing.JLabel jLabel1_2;
    private javax.swing.JLabel jLabel1_3;
    private javax.swing.JLabel jLabel1_4;
    private javax.swing.JLabel jLabel1_5;
    private javax.swing.JLabel jLabel2_1;
    private javax.swing.JLabel jLabel2_2;
    private javax.swing.JLabel jLabel2_3;
    private javax.swing.JLabel jLabel2_4;
    private javax.swing.JLabel jLabel2_5;
    private javax.swing.JLabel jLabel3_1;
    private javax.swing.JLabel jLabel3_2;
    private javax.swing.JLabel jLabel3_3;
    private javax.swing.JLabel jLabel3_4;
    private javax.swing.JLabel jLabel3_5;
    private javax.swing.JLabel jLabel4_1;
    private javax.swing.JLabel jLabel4_2;
    private javax.swing.JLabel jLabel4_3;
    private javax.swing.JLabel jLabel4_4;
    private javax.swing.JLabel jLabel4_5;
    private javax.swing.JLabel jLabel5_1;
    private javax.swing.JLabel jLabel5_2;
    private javax.swing.JLabel jLabel5_3;
    private javax.swing.JLabel jLabel5_4;
    private javax.swing.JLabel jLabel5_5;
    private javax.swing.JLabel jLabel6_1;
    private javax.swing.JLabel jLabel6_2;
    private javax.swing.JLabel jLabel6_3;
    private javax.swing.JLabel jLabel6_4;
    private javax.swing.JLabel jLabel6_5;
    private javax.swing.JPanel letrasjPanel;
    private javax.swing.JPanel mainjPanel;
    private javax.swing.JLabel maljLabel;
    private javax.swing.JPanel maljPanel;
    private javax.swing.JMenuBar menujMenuBar;
    private javax.swing.ButtonGroup motorbuttonGroup;
    private javax.swing.JMenuItem nuevojMenuItem;
    private javax.swing.JTextField palabrasjTextField;
    private javax.swing.JMenuItem salirjMenuItem;
    private javax.swing.JRadioButtonMenuItem testjRadioButtonMenuItem;
    // End of variables declaration//GEN-END:variables

    public void run() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
