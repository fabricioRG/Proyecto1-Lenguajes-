package analizador.analizadorlexico.frontend;

import analizador.analizadorlexico.backend.ManejadorAreaTexto;
import analizador.tokens.backend.ErrorLexema;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JEditorPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.UndoManager;
import org.jdesktop.observablecollections.ObservableCollections;
import org.jdesktop.observablecollections.ObservableList;

/**
 *
 * @author fabricio
 */
public class AreaTexto extends javax.swing.JPanel implements KeyListener, MouseListener {

    public String textoIngresado = "";
    private ManejadorAreaTexto mat = null;
    private UndoManager undoManager = null;
    private List<ErrorLexema> listaErrores = null;
    private ObservableList<ErrorLexema> listaErrorObser = null;
    private boolean modificado = false;

    /**
     * Creates new form AreaTexto
     */
    public AreaTexto() {
        this.listaErrores = new LinkedList<>();
        this.listaErrorObser = ObservableCollections.observableList(listaErrores);
        initComponents();
        jEditorPaneTexto.addKeyListener(this);
        jEditorPaneTexto.addMouseListener(this);
        mat = new ManejadorAreaTexto(this);
        undoManager = new UndoManager();
        jPanelErrores.setVisible(false); //Panel no visible hasta que se encuentre un error o se llame por aparte
        jButtonRedo.setEnabled(false); //Botones desabilitados hasta que se escriba en el area de texto
        jButtonUndo.setEnabled(false);
        jButtonPaste.setEnabled(false);
        jButtonCopy.setEnabled(false);
        
        //Funciones que implementan la funcion "Rehacer" y "Deshacer"
        jEditorPaneTexto.getDocument().addUndoableEditListener(new UndoableEditListener() {
            public void undoableEditHappened(UndoableEditEvent e) {
            undoManager.addEdit(e.getEdit());
            updateButtons();
          }
        });
        jButtonUndo.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        try {
          undoManager.undo();
        } catch (CannotRedoException cre) {
          cre.printStackTrace();
        }
        updateButtons();
        mat.iniciarAutomata();
      }
    });
    jButtonRedo.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        try {
          undoManager.redo();
        } catch (CannotRedoException cre) {
          cre.printStackTrace();
        }
        updateButtons();
        mat.iniciarAutomata();
      }
    });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        jLabel4 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jEditorPaneTexto = new javax.swing.JEditorPane();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabelColummna = new javax.swing.JLabel();
        jLabelLinea = new javax.swing.JLabel();
        jPanelErrores = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTableErrores = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        jButtonUndo = new javax.swing.JButton();
        jButtonRedo = new javax.swing.JButton();
        jButtonCopy = new javax.swing.JButton();
        jButtonPaste = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabelCoincidencias = new javax.swing.JLabel();
        jLabelCadena = new javax.swing.JLabel();

        jLabel4.setText("jLabel4");

        setPreferredSize(new java.awt.Dimension(844, 455));

        jEditorPaneTexto.setBackground(new java.awt.Color(2, 36, 61));
        jEditorPaneTexto.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        jEditorPaneTexto.setContentType("text/html"); // NOI18N
        jEditorPaneTexto.setFont(new java.awt.Font("Caviar Dreams", 0, 12)); // NOI18N
        jEditorPaneTexto.setForeground(new java.awt.Color(254, 254, 254));
        jEditorPaneTexto.setText("<html>\n<head>\n</head>\n<body style=\"color:white;font-family:Open Sans Light;padding:4px;\">\n<pre>\n</pre>\n</body>\n</html>\n");
        jEditorPaneTexto.setToolTipText("");
        jEditorPaneTexto.setCaretColor(new java.awt.Color(254, 254, 254));
        jEditorPaneTexto.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jScrollPane1.setViewportView(jEditorPaneTexto);

        jLabel1.setFont(new java.awt.Font("Open Sans", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(2, 36, 61));
        jLabel1.setText("Linea:");

        jLabel2.setFont(new java.awt.Font("Open Sans", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(2, 36, 61));
        jLabel2.setText("Columna:");

        jLabelColummna.setForeground(new java.awt.Color(2, 36, 61));
        jLabelColummna.setText(" ");

        jLabelLinea.setForeground(new java.awt.Color(2, 36, 61));
        jLabelLinea.setText(" ");

        jPanelErrores.setBackground(new java.awt.Color(2, 36, 61));
        jPanelErrores.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));

        org.jdesktop.beansbinding.ELProperty eLProperty = org.jdesktop.beansbinding.ELProperty.create("${listaErrorObser}");
        org.jdesktop.swingbinding.JTableBinding jTableBinding = org.jdesktop.swingbinding.SwingBindings.createJTableBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, eLProperty, jTableErrores);
        org.jdesktop.swingbinding.JTableBinding.ColumnBinding columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${nombreLexema}"));
        columnBinding.setColumnName("Lexema");
        columnBinding.setColumnClass(String.class);
        bindingGroup.addBinding(jTableBinding);
        jTableBinding.bind();
        jScrollPane2.setViewportView(jTableErrores);

        jLabel3.setFont(new java.awt.Font("Caviar Dreams", 0, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(254, 254, 254));
        jLabel3.setText("Lexemas no validos");

        javax.swing.GroupLayout jPanelErroresLayout = new javax.swing.GroupLayout(jPanelErrores);
        jPanelErrores.setLayout(jPanelErroresLayout);
        jPanelErroresLayout.setHorizontalGroup(
            jPanelErroresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelErroresLayout.createSequentialGroup()
                .addContainerGap(17, Short.MAX_VALUE)
                .addGroup(jPanelErroresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelErroresLayout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelErroresLayout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(21, 21, 21))))
        );
        jPanelErroresLayout.setVerticalGroup(
            jPanelErroresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelErroresLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );

        jButtonUndo.setBackground(new java.awt.Color(37, 110, 192));
        jButtonUndo.setForeground(new java.awt.Color(254, 254, 254));
        jButtonUndo.setText("Undo");
        jButtonUndo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonUndoActionPerformed(evt);
            }
        });

        jButtonRedo.setBackground(new java.awt.Color(55, 162, 201));
        jButtonRedo.setForeground(new java.awt.Color(254, 254, 254));
        jButtonRedo.setText("Redo");
        jButtonRedo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRedoActionPerformed(evt);
            }
        });

        jButtonCopy.setBackground(new java.awt.Color(255, 171, 0));
        jButtonCopy.setForeground(new java.awt.Color(254, 254, 254));
        jButtonCopy.setText("Copy");
        jButtonCopy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCopyActionPerformed(evt);
            }
        });

        jButtonPaste.setBackground(new java.awt.Color(219, 32, 28));
        jButtonPaste.setForeground(new java.awt.Color(254, 254, 254));
        jButtonPaste.setText("Paste");
        jButtonPaste.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonPasteActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Open Sans", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(2, 36, 61));
        jLabel5.setText("Cadena:");

        jLabel6.setFont(new java.awt.Font("Open Sans", 1, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(2, 36, 61));
        jLabel6.setText("Coincidencias:");

        jLabelCoincidencias.setForeground(new java.awt.Color(2, 36, 61));
        jLabelCoincidencias.setText(" ");

        jLabelCadena.setForeground(new java.awt.Color(2, 36, 61));
        jLabelCadena.setText(" ");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane1)
                .addGap(0, 0, 0)
                .addComponent(jPanelErrores, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelLinea, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelColummna, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(51, 51, 51)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelCadena, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelCoincidencias, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 112, Short.MAX_VALUE)
                .addComponent(jButtonCopy)
                .addGap(18, 18, 18)
                .addComponent(jButtonPaste)
                .addGap(18, 18, 18)
                .addComponent(jButtonUndo)
                .addGap(18, 18, 18)
                .addComponent(jButtonRedo)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 502, Short.MAX_VALUE)
                    .addComponent(jPanelErrores, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(4, 4, 4)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1)
                    .addComponent(jLabelColummna)
                    .addComponent(jLabelLinea)
                    .addComponent(jButtonUndo, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonRedo, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButtonCopy, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButtonPaste, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel6)
                        .addComponent(jLabel5)
                        .addComponent(jLabelCoincidencias)
                        .addComponent(jLabelCadena)))
                .addGap(0, 0, 0))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        bindingGroup.bind();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonUndoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonUndoActionPerformed
    }//GEN-LAST:event_jButtonUndoActionPerformed

    private void jButtonRedoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRedoActionPerformed
    }//GEN-LAST:event_jButtonRedoActionPerformed

    private void jButtonPasteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonPasteActionPerformed
        jEditorPaneTexto.paste();
        mat.iniciarAutomata();
    }//GEN-LAST:event_jButtonPasteActionPerformed

    private void jButtonCopyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCopyActionPerformed
        jEditorPaneTexto.copy();
    }//GEN-LAST:event_jButtonCopyActionPerformed

    public JEditorPane getjEditorPane1() {
        return jEditorPaneTexto;
    }

    public void setjEditorPane1(JEditorPane jEditorPane1) {
        this.jEditorPaneTexto = jEditorPane1;
    }

    public JPanel getjPanelErrores() {
        return jPanelErrores;
    }

    public void setjPanelErrores(JPanel jPanelErrores) {
        this.jPanelErrores = jPanelErrores;
    }

    public ManejadorAreaTexto getMat() {
        return mat;
    }

    public void setMat(ManejadorAreaTexto mat) {
        this.mat = mat;
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
        jLabelColummna.setText(Integer.toString(mat.getColumn())); //Funcion encargada de obtener la columna del cursor
        jLabelLinea.setText(Integer.toString(mat.getLine())); //Funcion encargada de obtener la linea del cursor
        mat.iniciarAutomata(); //Metodo encargado de iniciar el automata
        this.modificado = true;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        jLabelColummna.setText(Integer.toString(mat.getColumn())); //Funcion encargada de obtener la columna del cursor
        jLabelLinea.setText(Integer.toString(mat.getLine())); //Funcion encargada de obtener la linea del cursor
        jButtonCopy.setEnabled(true);
        jButtonPaste.setEnabled(true);
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
    
    public void actualizarErrores(List errores){
        this.listaErrorObser.clear();
        this.listaErrorObser.addAll(errores);
    }
    
    //Metodo encargado de actualizar el estado de los botones segun "Undo Manager"
    public void updateButtons(){
        jButtonUndo.setEnabled(undoManager.canUndo());
        jButtonRedo.setEnabled(undoManager.canRedo());
    }

    public ObservableList<ErrorLexema> getListaErrorObser() {
        return listaErrorObser;
    }

    public void setListaErrorObser(ObservableList<ErrorLexema> listaErrorObser) {
        this.listaErrorObser = listaErrorObser;
    }
    
    public void mostrarTokens(){
        mat.mostrarTokens();
    }

    public JLabel getjLabelCadena() {
        return jLabelCadena;
    }

    public void setjLabelCadena(JLabel jLabelCadena) {
        this.jLabelCadena = jLabelCadena;
    }

    public JLabel getjLabelCoincidencias() {
        return jLabelCoincidencias;
    }

    public void setjLabelCoincidencias(JLabel jLabelCoincidencias) {
        this.jLabelCoincidencias = jLabelCoincidencias;
    }

    public boolean isModificado() {
        return modificado;
    }

    public void setModificado(boolean modificado) {
        this.modificado = modificado;
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonCopy;
    private javax.swing.JButton jButtonPaste;
    private javax.swing.JButton jButtonRedo;
    private javax.swing.JButton jButtonUndo;
    private javax.swing.JEditorPane jEditorPaneTexto;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabelCadena;
    private javax.swing.JLabel jLabelCoincidencias;
    private javax.swing.JLabel jLabelColummna;
    private javax.swing.JLabel jLabelLinea;
    private javax.swing.JPanel jPanel1;
    public javax.swing.JPanel jPanelErrores;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    public javax.swing.JTable jTableErrores;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables

}
