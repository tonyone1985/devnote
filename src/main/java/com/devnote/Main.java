package com.devnote;

import com.devnote.mapping.FileMapper;
import com.devnote.mapping.NoteMapper;
import com.devnote.mapping.Testmapper;
import com.devnote.models.Notebean;
import com.devnote.models.TestModel;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.sqlite.SQLiteDataSource;

import javax.security.auth.login.AppConfigurationEntry;
import javax.sql.DataSource;
import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

public class Main {


    public static void main(String[] args) throws IOException {

        System.out.println(args);
        Properties properties =  new Properties();
        properties.setProperty("db","D:\\src\\go\\devnote\\dn.db");
        if (args.length>0) {
             // 加载数据库配置文件
             properties.setProperty("db",args[0]);
            //System.setProperty("db", args[0]);
        }



        String resource = "mybatis.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);



        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream,properties);



        sqlSessionFactory.getConfiguration().addMapper(NoteMapper.class);
        sqlSessionFactory.getConfiguration().addMapper(FileMapper.class);



//        SqlSession ses = sqlSessionFactory.openSession();
//        List<Notebean> tmm= ses.getMapper(NoteMapper.class).selectNotes("test");
//
//        System.out.println(tmm.get(0).getContent());
//        System.out.println(tmm.get(0).getTitle());
//        tmm.get(0).setContent(tmm.get(0).getContent()+"a");
//        ses.getMapper(NoteMapper.class).update(tmm.get(0));
//        ses.commit();
//        ses.close();


        JFrame frame = new JFrame();

        Devnote n = new Devnote(sqlSessionFactory);
        frame.setSize(800,600);
        frame.setContentPane(n.p0);
        frame.setVisible(true);
        frame.addWindowListener(
                new WindowAdapter() {


                    public void windowClosing(WindowEvent e) {
                        super.windowClosing(e);
                        //sqlSessionFactory.c
//加入动作
                        System.exit(1);
                    }

                });
        //方法体
    }
}
