package pe.gob.munihuacho.municipalidadhuacho.adapters;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.CountDownTimer;

/**
 * Created by peral on 4/04/2017.
 */

public abstract class AsynctaskWithDelayedIndeterminateProgress <Params,Progress,Result>
        extends AsyncTask<Params,Progress,Result> {
    private  static final int MIN_DELAY=250;
    private final ProgressDialog progressDialog;
    private final CountDownTimer countDownTimer;

    protected AsynctaskWithDelayedIndeterminateProgress(Activity activity) {
        progressDialog = createProgressDialog(activity);
        countDownTimer=createCountDownTimer();
    }

    @Override protected void onPreExecute() {
        countDownTimer.start();
    }

    @Override protected void onPostExecute(Result children) {
        countDownTimer.cancel();
        if(progressDialog.isShowing())
            progressDialog.dismiss();
    }

    private ProgressDialog createProgressDialog(Activity activity) {
        final ProgressDialog progressDialog = new ProgressDialog(activity);
        progressDialog.setIndeterminate(false);
        progressDialog.setTitle("Cargando Registros");
        progressDialog.setMessage("Por favor espere...");
        progressDialog.setMax(600);
        return progressDialog;
    }

    private CountDownTimer createCountDownTimer() {
        return new CountDownTimer(MIN_DELAY, MIN_DELAY + 1) {
            @Override public void onTick(long millisUntilFinished) { }

            @Override public void onFinish() {
                progressDialog.show();
            }
        };
    }
}
