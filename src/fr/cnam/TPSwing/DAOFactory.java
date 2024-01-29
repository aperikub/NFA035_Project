package fr.cnam.TPSwing;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;

public class DAOFactory {
    private DAOFactory(){

    }
    public static LivreDAO getDAO(){
        Properties props = new Properties();
        InputStream is = DAOFactory.class.getResourceAsStream("props.properties");
        try{
            props.load(is);
            is.close();
        }catch (IOException e){
            System.err.println(e.getMessage());
        }
        System.out.println("poperties value : " + props.getProperty("File_Path"));
        Class<?> classe = null;

        try {
            classe = Class.forName(props.getProperty("DAO_className"));
            //Arrays.stream(classe.getConstructors()).anyMatch(constructor -> (constructor.getParameterCount() == 0));
            return (LivreDAO) classe.getConstructor(null).newInstance();
        }catch (NoSuchMethodException er){
            for(Constructor<?> c : classe.getConstructors()){
                if (c.getParameterCount() == 0){
                    try {
                        return (LivreDAO) c.newInstance();
                    } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (ClassNotFoundException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

        return null;
    }
}
