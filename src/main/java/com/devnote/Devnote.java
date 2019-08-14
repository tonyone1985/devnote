package com.devnote;

import com.devnote.mapping.FileMapper;
import com.devnote.mapping.NoteMapper;
import com.devnote.models.Filebean;
import com.devnote.models.Notebean;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;

public class Devnote {
//    public static void com.devnote.main(String[] args)
//    {
//        JFrame frame = new JFrame();
//
//        Devnote n = new Devnote();
//        frame.setSize(800,600);
//        frame.setContentPane(n.p0);
//        frame.setVisible(true);
//        //方法体
//    }
    int mod;
    final int MOD_FILE=1;
    final int MOD_NOTE=0;

    public Devnote(final SqlSessionFactory sf){
        this.sf = sf;
        mod=MOD_NOTE;
        list2.setVisible(false);
        //searchButton.add
        searchButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                list2.setVisible(false);
                list1.setVisible(true);
                mod = MOD_NOTE;
                noteLabel.setText("note");

                SqlSession s = sf.openSession();
                List<Notebean> notes = s.getMapper(NoteMapper.class).selectNotes(textField1.getText().trim());
                s.close();;
                list1.setListData(notes.toArray());

            }
        });
        list1.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if(list1.getSelectedValue()==null)
                    return;
                Notebean note = (Notebean)list1.getSelectedValue();
                textArea1.setText(note.getContent());
                textField2.setText(note.getTitle());

            }
        });
        saveButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                if(mod ==  MOD_NOTE) {
                    if(list1.getSelectedValue()==null)
                        return;
                    int rst = JOptionPane.showConfirmDialog(p0, "confirm update", "update", JOptionPane.YES_NO_OPTION);
                    if (rst == 1) {
                        return;
                    }

                    Notebean note = (Notebean) list1.getSelectedValue();
                    note.setContent(textArea1.getText());
                    note.setTitle(textField2.getText());
                    SqlSession s = sf.openSession();
                    s.getMapper(NoteMapper.class).update(note);
                    s.commit();
                    s.close();
                }else{
                    if(list2.getSelectedValue()==null)
                        return;
                    int rst = JOptionPane.showConfirmDialog(p0, "confirm save file "+ textArea1.getText(), "update", JOptionPane.YES_NO_OPTION);
                    if (rst == 1) {
                        return;
                    }
                    Filebean f = (Filebean) list2.getSelectedValue();
                    SqlSession s = sf.openSession();
                    f = s.getMapper(FileMapper.class).selectFile(f.getId());

                    File fi = new File(textArea1.getText());
                    FileOutputStream fw = null;
                    try {
                        fw = new FileOutputStream(textArea1.getText());
                        byte[] bd =( byte[]) f.getContent();
                        fw.write(bd);
                        fw.close();
                    } catch (FileNotFoundException e1) {
                        e1.printStackTrace();
                    }catch (IOException e1) {
                        e1.printStackTrace();
                    }


                }

            }
        });
        addButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int rst = JOptionPane.showConfirmDialog(p0, "confirm insert "+noteLabel.getText(), "insert", JOptionPane.YES_NO_OPTION);
                if (rst == 1) {
                    return;
                }
                if(mod ==  MOD_NOTE) {


                    Notebean note = new Notebean();
                    note.setTitle(textField2.getText());
                    note.setContent(textArea1.getText());

                    SqlSession s = sf.openSession();
                    s.getMapper(NoteMapper.class).insert(note);
                    s.commit();
                    s.close();
                }else{

                    Filebean fb = new Filebean();
                    fb.setTitle(textField2.getText());
                    File f = new File(textArea1.getText());
                    InputStream is = null;
                    try {
                        is = new FileInputStream(f);
                        BufferedInputStream bis = new BufferedInputStream(is);
                        byte[] bf = new byte[(int)f.length()];
                        bis.read(bf,0,(int)bf.length);
                        //new Blob()
                        //fb.setContent(bf);
                        bis.close();
                        SqlSession s = sf.openSession();
                        s.getMapper(FileMapper.class).insert(fb);
                        s.commit();
                        s.close();

                    } catch (FileNotFoundException e1) {
                        e1.printStackTrace();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }



                }


            }
        });
        delButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                int rst = JOptionPane.showConfirmDialog(p0,"confirm delete "+noteLabel.getText(),"delete",JOptionPane.YES_NO_OPTION );
                if (rst == 1)
                {
                    return;
                }

                if(mod ==  MOD_NOTE) {

                    Notebean note = (Notebean) list1.getSelectedValue();
                    SqlSession s = sf.openSession();
                    s.getMapper(NoteMapper.class).del(note.getId());
                    s.commit();
                    s.close();
                }else{
                    Filebean file = (Filebean) list2.getSelectedValue();
                    SqlSession s = sf.openSession();
                    s.getMapper(FileMapper.class).del(file.getId());
                    s.commit();
                    s.close();
                }


            }
        });

        filesButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                list1.setVisible(false);
                list2.setVisible(true);
                mod = MOD_FILE;
                noteLabel.setText("file");

                SqlSession s = sf.openSession();
                List<Filebean> files = s.getMapper(FileMapper.class).selectFiles(textField1.getText().trim());
                s.close();;
                list2.setListData(files.toArray());
            }
        });


        list2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if(e.getClickCount() <2)
                    return;


            }
        });
        list2.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                Filebean fff = (Filebean)list2.getSelectedValue();
                if (fff==null)
                {
                    return;
                }
                textField2.setText(fff.getTitle());
                textArea1.setText(fff.getTitle());
            }
        });
    }
    private SqlSessionFactory sf;
    private JTextArea textArea1;
    private JButton searchButton;
    private JTextField textField1;
    private JPanel p1;
    public JPanel p0;
    private JButton saveButton;
    private JList list1;
    private JButton addButton;
    private JButton delButton;
    private JTextField textField2;
    private JButton filesButton;
    private JList list2;
    private JLabel noteLabel;
}
