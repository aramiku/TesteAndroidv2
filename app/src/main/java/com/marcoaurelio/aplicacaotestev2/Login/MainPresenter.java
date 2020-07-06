package com.marcoaurelio.aplicacaotestev2.Login;

import android.util.Log;

import java.lang.ref.WeakReference;

interface MainPresenterMVP {
    void login(String email, String senha);
}

public class MainPresenter implements MainPresenterMVP{

    private WeakReference<MainActivityMVP> activity;
    private Usuario usr;

    public MainPresenter(MainActivityMVP referent) {
        //super(new WeakReference<>(referent));
        this.activity = new WeakReference<>(referent);
        usr = new Usuario(this);
    }
    @Override
    public void login(String email, String senha) {
        if (checkStringUpper(senha)){
            // Continua
            Log.d("login", "Senha formato correto, seguir..");
            if (checkStringSpecial(senha)){
                Log.d("login" , "Tem caractere especial , seguir..");
                if (checkStringNumber(senha)){
                    Log.d("login" , "Tem caractere numérico , seguir..");
                    usr.loginNetwork(email,senha);
                }else{
                    activity.get().resultLogin("Formato senha", "A senha deve conter ao menos um número .");
                }
            }else{
                activity.get().resultLogin("Formato senha", "A senha deve conter ao menos um caracter especial .");
            }
        }else{
            activity.get().resultLogin("Formato senha", "A senha deve conter ao menos um caracter em maiúsculo.");
        }

    }

    private static boolean checkStringMaster(String str) {
        char ch;
        boolean capitalFlag = false;
        boolean lowerCaseFlag = false;
        boolean numberFlag = false;
        for(int i=0;i < str.length();i++) {
            ch = str.charAt(i);
            if( Character.isDigit(ch)) {
                numberFlag = true;
            }
            else if (Character.isUpperCase(ch)) {
                capitalFlag = true;
            } else if (Character.isLowerCase(ch)) {
                lowerCaseFlag = true;
            }
            if(numberFlag && capitalFlag && lowerCaseFlag)
                return true;
        }
        return false;
    }

    private static boolean checkStringUpper(String str) {
        char ch;
        boolean capitalFlag = false;
        for(int i=0;i < str.length();i++) {
            ch = str.charAt(i);
            if (Character.isUpperCase(ch)) {
                capitalFlag = true;
            }
            if(capitalFlag)
                return true;
        }
        return false;
    }

    private static boolean checkStringSpecial(String str) {
        char ch;
        String strSpe = "%#$@?!:;";
        boolean specialFlag = false;
        for(int i=0;i < str.length();i++) {
            ch = str.charAt(i);
            if (strSpe.contains(String.valueOf(ch))) {
                specialFlag = true;
            }
            if(specialFlag)
                return true;
        }
        return false;
    }
    private static boolean checkStringNumber(String str) {
        char ch;
        boolean numberFlag = false;
        for(int i=0;i < str.length();i++) {
            ch = str.charAt(i);
            if( Character.isDigit(ch)) {
                numberFlag = true;
            }

            if(numberFlag)
                return true;
        }
        return false;
    }
    public void passarDadosPessoais(String userId, String name, String bankAccount, String agency, String balance){
        activity.get().gravarDadosPessoais(userId, name, bankAccount, agency, balance);
    }

    public void rotaATomar(Class cras){
        activity.get().rotaATomar(cras);
    }

    //if(s.contains("+"))

}
