package com.softdesign.devintensive.ui.activities;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.softdesign.devintensive.R;
import com.softdesign.devintensive.utils.ConstantManager;

public class BaseActivity extends AppCompatActivity {
    static final String TAG = ConstantManager.TAG_PREFIX + "BaseActivity";
    protected ProgressDialog mProgressDialog; //Класс который позволяет показать пользователю что идет загрузка

    /**
     * Метод для отображения загрузчика
     */
    public void showProgress() {
        if (mProgressDialog == null) { //если рогресс диалог не создавался то
            mProgressDialog = new ProgressDialog(this, R.style.custom_dialog); // создать новый, контекст, стиль
            mProgressDialog.setCancelable(false);//Блокировка закрытия дилога системной клаишей НАЗАД
            mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));//Получаем окно диалога и устанавливаем фон
            mProgressDialog.show();
            mProgressDialog.setContentView(R.layout.progress_splash);//Вставить сверстаный макет
        } else {
            mProgressDialog.show();
            mProgressDialog.setContentView(R.layout.progress_splash);
        }
    }

    /**
     * Метод для сокрытия загрузчика
     */
    public void hideProgress(){
        if(mProgressDialog!=null){
            if(mProgressDialog.isShowing()){
                mProgressDialog.hide();
            }
        }

    }

    /**
     * Вывод сообщение об ошибке
     * Реализация: при помощи метода showToast выводим сообщение(message) на экран
     * Логируем ошибку
     * @param message
     * @param error
     */
    public void showError(String message, Exception error){
        showToast(message);
        Log.e(TAG, String.valueOf(error));
    }

    /**
     * Вывод сплывающего сообщения в полупрозрачном овале
     * Реализация: вызвать static метод makeText на обхекте Toast передать контекст = this, сообщение и длительность показа
     * @param message
     */

    public void showToast(String message){
        Toast.makeText(this, message, Toast.LENGTH_LONG);
    }
}
