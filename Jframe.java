package Cordiprogramas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import java.text.*;

public class Jframe extends JFrame {
    // Declaración de controles
    JLabel triedLabel = new JLabel();
    JTextField triedTextField = new JTextField();
    JLabel correctLabel = new JLabel();
    JTextField correctTextField = new JTextField();
    JLabel problemLabel = new JLabel();
    JLabel dividerLabel = new JLabel();
    JPanel typePanel = new JPanel();
    JCheckBox[] typeCheckBox = new JCheckBox[4];
    JPanel factorPanel = new JPanel();
    ButtonGroup factorButtonGroup = new ButtonGroup();
    JRadioButton[] factorRadioButton = new JRadioButton[11];
    JPanel timerPanel = new JPanel();
    ButtonGroup timerButtonGroup = new ButtonGroup();
    JRadioButton[] timerRadioButton = new JRadioButton[3];
    JTextField timerTextField = new JTextField();
    JScrollBar timerScrollBar = new JScrollBar();
    JButton startButton = new JButton();
    JButton exitButton = new JButton();
    Timer problemsTimer;

    // Fuente y colores
    Font myFont = new Font("Arial", Font.PLAIN, 18);
    Color lightBlue = new Color(192, 192, 255);

    // Variables para el manejo de problemas y respuestas
    Random myRandom = new Random();
    int numberTried, numberCorrect;
    int correctAnswer, numberDigits;
    String problem;
    String yourAnswer;
    int digitNumber;
    int problemTime;

    public static void main(String[] args) {
        // Crear y mostrar el frame
        new Jframe().setVisible(true);
    }

    public Jframe() {
        // Configuración del frame
        setTitle("Flash Card Math");
        getContentPane().setBackground(new Color(255, 255, 192));
        setResizable(false);

        // Listener para cerrar la ventana
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent evt) {
                exitForm(evt);
            }
        });

        // Configuración del layout
        getContentPane().setLayout(new GridBagLayout());
        GridBagConstraints gridConstraints;

        // Configuración de los controles
        triedLabel.setText("Intentos");
        triedLabel.setFont(myFont);
        gridConstraints = new GridBagConstraints();
        gridConstraints.gridx = 0;
        gridConstraints.gridy = 0;
        gridConstraints.anchor = GridBagConstraints.WEST;
        gridConstraints.insets = new Insets(10, 10, 0, 10);
        getContentPane().add(triedLabel, gridConstraints);

        triedTextField.setText("0");
        triedTextField.setPreferredSize(new Dimension(90, 30));
        triedTextField.setEditable(false);
        triedTextField.setBackground(Color.RED);
        triedTextField.setForeground(Color.YELLOW);
        triedTextField.setHorizontalAlignment(SwingConstants.CENTER);
        triedTextField.setFont(myFont);
        gridConstraints = new GridBagConstraints();
        gridConstraints.gridx = 1;
        gridConstraints.gridy = 0;
        gridConstraints.insets = new Insets(10, 0, 0, 0);
        getContentPane().add(triedTextField, gridConstraints);

        correctLabel.setText("Correctas:");
        correctLabel.setFont(myFont);
        gridConstraints = new GridBagConstraints();
        gridConstraints.gridx = 2;
        gridConstraints.gridy = 0;
        gridConstraints.anchor = GridBagConstraints.EAST;
        gridConstraints.insets = new Insets(10, 10, 0, 10);
        getContentPane().add(correctLabel, gridConstraints);

        correctTextField.setText("0");
        correctTextField.setPreferredSize(new Dimension(90, 30));
        correctTextField.setEditable(false);
        correctTextField.setBackground(Color.RED);
        correctTextField.setForeground(Color.YELLOW);
        correctTextField.setHorizontalAlignment(SwingConstants.CENTER);
        correctTextField.setFont(myFont);
        gridConstraints = new GridBagConstraints();
        gridConstraints.gridx = 3;
        gridConstraints.gridy = 0;
        gridConstraints.insets = new Insets(10, 0, 0, 0);
        getContentPane().add(correctTextField, gridConstraints);

        problemLabel.setText("");
        problemLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        problemLabel.setPreferredSize(new Dimension(450, 100));
        problemLabel.setBackground(Color.WHITE);
        problemLabel.setOpaque(true);
        problemLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 48));
        problemLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gridConstraints = new GridBagConstraints();
        gridConstraints.gridx = 0;
        gridConstraints.gridy = 1;
        gridConstraints.gridwidth = 5;
        gridConstraints.insets = new Insets(10, 10, 0, 10);
        getContentPane().add(problemLabel, gridConstraints);

        problemLabel.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                problemLabelKeyPressed(e);
            }
        });

        dividerLabel.setPreferredSize(new Dimension(450, 10));
        dividerLabel.setBackground(Color.RED);
        dividerLabel.setOpaque(true);
        gridConstraints = new GridBagConstraints();
        gridConstraints.gridx = 0;
        gridConstraints.gridy = 2;
        gridConstraints.gridwidth = 5;
        gridConstraints.insets = new Insets(10, 10, 10, 10);
        getContentPane().add(dividerLabel, gridConstraints);

        // Configuración del panel de tipo de problema
        UIManager.put("TitledBorder.font", new Font("Arial", Font.BOLD, 14));
        typePanel.setPreferredSize(new Dimension(130, 130));
        typePanel.setBorder(BorderFactory.createTitledBorder("Tipo:"));
        typePanel.setBackground(lightBlue);
        typePanel.setLayout(new GridBagLayout());
        gridConstraints = new GridBagConstraints();
        gridConstraints.gridx = 0;
        gridConstraints.gridy = 3;
        gridConstraints.gridwidth = 2;
        gridConstraints.anchor = GridBagConstraints.NORTH;
        getContentPane().add(typePanel, gridConstraints);

        for (int i = 0; i < 4; i++) {
            typeCheckBox[i] = new JCheckBox();
            typeCheckBox[i].setBackground(lightBlue);
            gridConstraints = new GridBagConstraints();
            gridConstraints.gridx = 0;
            gridConstraints.gridy = i;
            gridConstraints.anchor = GridBagConstraints.WEST;
            typePanel.add(typeCheckBox[i], gridConstraints);
            typeCheckBox[i].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    typeCheckBoxActionPerformed(e);
                }
            });
        }

        typeCheckBox[0].setText("Suma");
        typeCheckBox[1].setText("Resta");
        typeCheckBox[2].setText("Multiplicacion");
        typeCheckBox[3].setText("Division");
        typeCheckBox[0].setSelected(true);

        // Configuración del panel de factor
        factorPanel.setPreferredSize(new Dimension(130, 130));
        factorPanel.setBorder(BorderFactory.createTitledBorder("Factor:"));
        factorPanel.setBackground(lightBlue);
        factorPanel.setLayout(new GridBagLayout());
        gridConstraints = new GridBagConstraints();
        gridConstraints.gridx = 2;
        gridConstraints.gridy = 3;
        gridConstraints.gridwidth = 2;
        gridConstraints.anchor = GridBagConstraints.NORTH;
        getContentPane().add(factorPanel, gridConstraints);

        int x = 2;
        int y = 0;
        for (int i = 0; i < 11; i++) {
            factorRadioButton[i] = new JRadioButton();
            factorRadioButton[i].setText(String.valueOf(i));
            factorRadioButton[i].setBackground(lightBlue);
            factorButtonGroup.add(factorRadioButton[i]);
            gridConstraints = new GridBagConstraints();
            if (i < 10) {
                gridConstraints.gridx = x;
                gridConstraints.gridy = y;
            } else {
                gridConstraints.gridx = 0;
                gridConstraints.gridy = 0;
                gridConstraints.gridwidth = 2;
            }
            gridConstraints.anchor = GridBagConstraints.WEST;
            factorPanel.add(factorRadioButton[i], gridConstraints);
            factorRadioButton[i].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    factorRadioButtonActionPerformed(e);
                }
            });
            x++;
            if (x > 2) {
                x = 0;
                y++;
            }
        }

        factorRadioButton[10].setText("Aleatorio");
        factorRadioButton[10].setSelected(true);

        // Configuración del panel de temporizador
        timerPanel.setPreferredSize(new Dimension(130, 130));
        timerPanel.setBorder(BorderFactory.createTitledBorder("Tiempo:"));
        timerPanel.setBackground(lightBlue);
        timerPanel.setLayout(new GridBagLayout());
        gridConstraints = new GridBagConstraints();
        gridConstraints.gridx = 4;
        gridConstraints.gridy = 3;
        gridConstraints.insets = new Insets(0, 0, 0, 10);
        gridConstraints.anchor = GridBagConstraints.NORTH;
        getContentPane().add(timerPanel, gridConstraints);

        for (int i = 0; i < 3; i++) {
            timerRadioButton[i] = new JRadioButton();
            timerRadioButton[i].setBackground(lightBlue);
            timerButtonGroup.add(timerRadioButton[i]);
            gridConstraints = new GridBagConstraints();
            gridConstraints.gridx = 0;
            gridConstraints.gridy = i;
            gridConstraints.gridwidth = 2;
            gridConstraints.anchor = GridBagConstraints.WEST;
            timerPanel.add(timerRadioButton[i], gridConstraints);
            timerRadioButton[i].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    timerRadioButtonActionPerformed(e);
                }
            });
        }

        timerRadioButton[0].setText("Apagado");
        timerRadioButton[1].setText("En cuenta arriba");
        timerRadioButton[2].setText("En cuenta abajo");
        timerRadioButton[0].setSelected(true);

        timerTextField.setText("Off");
        timerTextField.setPreferredSize(new Dimension(90, 25));
        timerTextField.setEditable(false);
        timerTextField.setBackground(Color.WHITE);
        timerTextField.setForeground(Color.RED);
        timerTextField.setHorizontalAlignment(SwingConstants.CENTER);
        timerTextField.setFont(myFont);
        gridConstraints = new GridBagConstraints();
        gridConstraints.gridx = 0;
        gridConstraints.gridy = 3;
        gridConstraints.anchor = GridBagConstraints.WEST;
        gridConstraints.insets = new Insets(5, 0, 0, 0);
        timerPanel.add(timerTextField, gridConstraints);

        timerScrollBar.setPreferredSize(new Dimension(20, 25));
        timerScrollBar.setMinimum(1);
        timerScrollBar.setMaximum(60);
        timerScrollBar.setValue(1);
        timerScrollBar.setBlockIncrement(1);
        timerScrollBar.setUnitIncrement(1);
        timerScrollBar.setOrientation(JScrollBar.VERTICAL);
        timerScrollBar.setEnabled(false);
        gridConstraints = new GridBagConstraints();
        gridConstraints.gridx = 1;
        gridConstraints.gridy = 3;
        gridConstraints.anchor = GridBagConstraints.WEST;
        gridConstraints.insets = new Insets(5, 0, 0, 0);
        timerPanel.add(timerScrollBar, gridConstraints);

        timerScrollBar.addAdjustmentListener(new AdjustmentListener() {
            public void adjustmentValueChanged(AdjustmentEvent e) {
                timerScrollBarAdjustmentValueChanged(e);
            }
        });

        // Configuración de los botones de inicio y salida
        startButton.setText("Empezar Practicas");
        gridConstraints = new GridBagConstraints();
        gridConstraints.gridx = 0;
        gridConstraints.gridy = 4;
        gridConstraints.gridwidth = 2;
        gridConstraints.insets = new Insets(10, 0, 10, 0);
        getContentPane().add(startButton, gridConstraints);
        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                startButtonActionPerformed(e);
            }
        });

        exitButton.setText("Salir");
        gridConstraints = new GridBagConstraints();
        gridConstraints.gridx = 2;
        gridConstraints.gridy = 4;
        gridConstraints.gridwidth = 2;
        gridConstraints.insets = new Insets(10, 0, 10, 0);
        getContentPane().add(exitButton, gridConstraints);
        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                exitButtonActionPerformed(e);
            }
        });

        // Configuración del temporizador
        problemsTimer = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                problemsTimerActionPerformed(e);
            }
            
             });

        pack();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((int) (0.5 * (screenSize.width - getWidth())), (int) (0.5 * (screenSize.height - getHeight())), getWidth(), getHeight());
    }

    private void exitForm(WindowEvent evt) {
        System.exit(0);
    }

    private void typeCheckBoxActionPerformed(ActionEvent e) {
        int numberChecks;
        int clickedBox = 0;
        String s = e.getActionCommand();

        if (s.equals("Suma")) clickedBox = 0;
        else if (s.equals("Resta")) clickedBox = 1;
        else if (s.equals("Multiplicacion")) clickedBox = 2;
        else if (s.equals("Division")) clickedBox = 3;

        numberChecks = 0;
        if (typeCheckBox[0].isSelected()) numberChecks++;
        if (typeCheckBox[1].isSelected()) numberChecks++;
        if (typeCheckBox[2].isSelected()) numberChecks++;
        if (typeCheckBox[3].isSelected()) {
            numberChecks++;
            if (factorRadioButton[0].isSelected()) factorRadioButton[1].doClick();
            factorRadioButton[0].setEnabled(false);
        } else {
            factorRadioButton[0].setEnabled(true);
        }

        if (numberChecks == 0) typeCheckBox[clickedBox].setSelected(true);
        problemLabel.requestFocus();
    }

    private void factorRadioButtonActionPerformed(ActionEvent e) {
        problemLabel.requestFocus();
    }

    private void timerRadioButtonActionPerformed(ActionEvent e) {
        if (timerRadioButton[0].isSelected()) {
            timerTextField.setText("Off");
            timerScrollBar.setEnabled(false);
        } else if (timerRadioButton[1].isSelected()) {
            problemTime = 0;
            timerTextField.setText(getTime(problemTime));
            timerScrollBar.setEnabled(false);
        } else if (timerRadioButton[2].isSelected()) {
            problemTime = 30 * timerScrollBar.getValue();
            timerTextField.setText(getTime(problemTime));
            timerScrollBar.setEnabled(true);
        }
    }

    private void timerScrollBarAdjustmentValueChanged(AdjustmentEvent e) {
        timerTextField.setText(getTime(30 * timerScrollBar.getValue()));
    }

    private void startButtonActionPerformed(ActionEvent e) {
        int score;
        String message = "";

        if (startButton.getText().equals("Empezar Practicas")) {
            startButton.setText("Parar practicas");
            exitButton.setEnabled(false);
            numberTried = 0;
            numberCorrect = 0;
            triedTextField.setText("0");
            correctTextField.setText("0");
            timerRadioButton[0].setEnabled(false);
            timerRadioButton[1].setEnabled(false);
            timerRadioButton[2].setEnabled(false);
            timerScrollBar.setEnabled(false);

            if (timerRadioButton[0].isSelected()) {
                if (timerRadioButton[1].isSelected()) problemTime = 0;
                else problemTime = 30 * timerScrollBar.getValue();
                timerTextField.setText(getTime(problemTime));
                problemsTimer.start();
            }

            problemLabel.setText(getProblem());
        } else {
            timerRadioButton[0].setEnabled(true);
            timerRadioButton[1].setEnabled(true);
            timerRadioButton[2].setEnabled(true);
            if (timerRadioButton[2].isSelected()) timerScrollBar.setEnabled(true);
            problemsTimer.stop();
            startButton.setText("Empezar Practicas");
            exitButton.setEnabled(true);
            problemLabel.setText("");

            if (numberTried > 0) {
                score = (int) (100 * (double) (numberCorrect) / numberTried);
                message = "Problemas: " + String.valueOf(numberTried) + "\n";
                message += "Problemas Correctas: " + String.valueOf(numberCorrect) + " (" + String.valueOf(score) + "%)" + "\n";
                if (timerRadioButton[0].isSelected()) {
                    message += "Tiempo acabado";
                } else {
                    if (timerRadioButton[2].isSelected()) {
                        problemTime = 30 * timerScrollBar.getValue() - problemTime;
                    }
                    message += "Elapsed Time: " + getTime(problemTime) + "\n";
                    message += "Tiempo por problema: " + new DecimalFormat("0.00").format((double) (problemTime) / numberTried) + " sec";
                }
                JOptionPane.showConfirmDialog(null, message, "Resultado", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    private void exitButtonActionPerformed(ActionEvent e) {
        System.exit(0);
    }

    private void problemLabelKeyPressed(KeyEvent e) {
        if (startButton.getText().equals("Empezar Practicas")) return;
        // Solo permitir teclas numéricas
        if (e.getKeyChar() >= '0' && e.getKeyChar() <= '9') {
            yourAnswer += e.getKeyChar();
            problemLabel.setText(problem + yourAnswer);
            if (digitNumber != numberDigits) {
                digitNumber++;
                problemLabel.setText(problemLabel.getText() + "?");
                return;
            } else {
                numberTried++;
                // Verificar la respuesta
                if (Integer.valueOf(yourAnswer).intValue() == correctAnswer) {
                    numberCorrect++;
                }
                triedTextField.setText(String.valueOf(numberTried));
                correctTextField.setText(String.valueOf(numberCorrect));
                problemLabel.setText(getProblem());
            }
        }
    }

    private void problemsTimerActionPerformed(ActionEvent e) {
        if (timerRadioButton[1].isSelected()) {
            problemTime++;
            timerTextField.setText(getTime(problemTime));
            if (problemTime >= 1800) {
                startButton.doClick();
                return;
            }
        } else {
            problemTime--;
            timerTextField.setText(getTime(problemTime));
            if (problemTime == 0) {
                startButton.doClick();
                return;
            }
        }
    }

    private String getProblem() {
        int pType, p, number, factor;
        p = 0;
        do {
            pType = myRandom.nextInt(4) + 1;
            if (pType == 1 && typeCheckBox[0].isSelected()) {
                // Suma
                p = pType;
                number = myRandom.nextInt(10);
                factor = getFactor(1);
                correctAnswer = number + factor;
                problem = String.valueOf(number) + " + " + String.valueOf(factor) + " = ";
            } else if (pType == 2 && typeCheckBox[1].isSelected()) {
                // Resta
                p = pType;
                factor = getFactor(2);
                correctAnswer = myRandom.nextInt(10);
                number = correctAnswer + factor;
                problem = String.valueOf(number) + " - " + String.valueOf(factor) + " = ";
            } else if (pType == 3 && typeCheckBox[2].isSelected()) {
                // Multiplicación
                p = pType;
                number = myRandom.nextInt(10);
                factor = getFactor(3);
                correctAnswer = number * factor;
                problem = String.valueOf(number) + " x " + String.valueOf(factor) + " = ";
            } else if (pType == 4 && typeCheckBox[3].isSelected()) {
                // División
                p = pType;
                factor = getFactor(4);
                correctAnswer = myRandom.nextInt(10);
                number = correctAnswer * factor;
                problem = String.valueOf(number) + " / " + String.valueOf(factor) + " = ";
            }
        } while (p == 0);

        yourAnswer = "";
        digitNumber = 1;
        problemLabel.requestFocus();
        if (correctAnswer < 10) {
            numberDigits = 1;
            return (problem + "?");
        } else {
            numberDigits = 2;
            return (problem + "??");
        }
    }

    private int getFactor(int p) {
        if (factorRadioButton[10].isSelected()) {
            // Factor aleatorio
            if (p == 4) {
                return (myRandom.nextInt(9) + 1);
            } else {
                return (myRandom.nextInt(10));
            }
        } else {
            for (int i = 0; i < 10; i++) {
                if (factorRadioButton[i].isSelected()) return i;
            }
            return 0;
        }
    }

    private String getTime(int s) {
        int min, sec;
        String ms, ss;
        min = (int) (s / 60);
        sec = s - 60 * min;
        ms = String.valueOf(min);
        ss = String.valueOf(sec);
        if (sec < 10) ss = "0" + ss;
        return (ms + ":" + ss);
    }
}