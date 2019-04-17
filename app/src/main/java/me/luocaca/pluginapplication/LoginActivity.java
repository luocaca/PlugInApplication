package me.luocaca.pluginapplication;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.CursorLoader;
import android.content.Loader;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import me.luocaca.pluginapplication.http.HttpManager;
import me.luocaca.pluginapplication.util.JsonFormateUtil;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.BufferedSink;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends Activity implements LoaderCallbacks<Cursor> {

    private static final String TAG = "LoginActivity";

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;

    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:hello", "bar@example.com:world"
    };
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private UserLoginTask mAuthTask = null;

    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;


    private ImageView verifycodeImg;

    List<Cookie> cookies = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.


        verifycodeImg = findViewById(R.id.verifycodeImg);
        mEmailView = (AutoCompleteTextView) findViewById(R.id.userName);
        populateAutoComplete();

        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);


        findViewById(R.id.getInfo).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {


                //点击获取个人信息

                OkHttpClient client = new OkHttpClient.Builder().cookieJar(new CookieJar() {
                    @Override
                    public void saveFromResponse(HttpUrl url, List<Cookie> coo) {
//                        LoginActivity.this.cookies = cookies;


                        Log.i(TAG, "saveFromResponse: " + coo.get(0).toString());


                        Cookie cookie = coo.get(0);


                        try {
                            if (!cookies.contains(cookie))
                                cookies.add(cookie);
                        } catch (Exception e) {

                            Log.w(TAG, "saveFromResponse: " + e.getMessage());
                        }

//                        cookies.addAll(coo);


//                        for (int i = 0; i < cookies.size(); i++) {
//                            if (!LoginActivity.this.cookies.contains(cookies.get(i))) {
//
//
//                                LoginActivity.this.cookies.addAll(cookies);
//
//
//
//
//                            } else {
//                                Log.i(TAG, "saveFromResponse: no contain to add");
//                            }
//
//                        }


                        for (int i = 0; i < cookies.size(); i++) {
                            Log.i(TAG, "cookie: " + cookies.get(i).toString());
                        }

                    }

                    @Override
                    public List<Cookie> loadForRequest(HttpUrl url) {
                        return cookies;
                    }
                }).build();

                final Request request = new Request.Builder()

                        .url("http://www.ahjxjy.cn/study/home/userInfo?courseOpenId=gbrqan2ppl1jeegaopet9g&schoolCode=003")
                        .get()
//                        .addHeader("Cookie", "ASP.NET_SessionId=unestvsstmq0nxbe0053edeb; auth=0102535F805D48C2D608FE531FEA8711C3D6080016680073006F00660061006E006500700079007A00760067002D0078006200730034006300640032003000670000012F00FFA72219F26454785012FC48D22FD0DE6A9DFD3FB7")
//                        .addHeader("Cookie", "auth=0102535F805D48C2D608FE531FEA8711C3D6080016680073006F00660061006E006500700079007A00760067002D0078006200730034006300640032003000670000012F00FFA72219F26454785012FC48D22FD0DE6A9DFD3FB7")
//                        .addHeader("Cookie", "lStatistic=jty46bb1; ASP.NET_SessionId=n4d4k1pwwku1sgohynwyjohs; jwplayer.captionLabel=Off; auth=0102D4DF07DDDEC2D608FED49F7107A8C3D608001662006A0075007800610064006B00710074007A007800670077006500710075006700670070006F002D00770000012F00FF791B45F37A8089EDE0266FBFA2B3B93C71990A60")
                        .addHeader("cache-control", "no-cache")
                        .addHeader("postman-token", "9282cee6-4c60-4761-a5af-3422be56be9a")
                        .build();

                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, final IOException e) {
                        final TextView tv = findViewById(R.id.info);
                        tv.post(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    tv.setText(e.getMessage());
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            }
                        });

                    }

                    @Override
                    public void onResponse(Call call, final Response response) throws IOException {
                        final TextView tv = findViewById(R.id.info);


                        tv.post(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Log.w(TAG, "run: " + Thread.currentThread());
                                    tv.setText(JsonFormateUtil.formatJson(response.body().string()) + "\n");
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                            }
                        });
                    }
                });


            }
        });


        findViewById(R.id.getList).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {


                Toast.makeText(LoginActivity.this, "hhh", Toast.LENGTH_SHORT).show();

                //点击获取个人信息

                OkHttpClient client = new OkHttpClient.Builder().cookieJar(new CookieJar() {
                    @Override
                    public void saveFromResponse(HttpUrl url, List<Cookie> co) {


                        Cookie cookie1 = co.get(0);


                        try {
                            cookies.add(cookie1);
                        } catch (Exception e) {

                        }

//                        Iterator iterator = co.iterator();
//
//                        while (iterator.hasNext()) {
//                            Object next = iterator.next();
//                            if (!cookies.contains(next)) {
//                                cookies.add((Cookie) next);
//                            }
//
//                        }

//                        for (int i = 0; i < cookies.size(); i++) {
//                            Cookie cookie = co.get(i);
//                            if (!cookies.contains(cookie)) {
//                                cookies.add(cookie);
//                            }
//                        }

                        for (Cookie cookie : cookies) {
                            Log.i(TAG, "getList -- cookie: " + cookie.toString());
                        }


                    }

                    @Override
                    public List<Cookie> loadForRequest(HttpUrl url) {
                        return cookies;
                    }
                }).build();

                final Request request = new Request.Builder()
                        .url("http://www.ahjxjy.cn/study/design/design?courseOpenId=gbrqan2plyjebi2f1vfkpa&schoolCode=003")
                        .method("POST", new RequestBody() {
                            @Override
                            public MediaType contentType() {
                                return null;
                            }

                            @Override
                            public void writeTo(BufferedSink sink) throws IOException {

                            }
                        })
//                        .addHeader("Cookie", "ASP.NET_SessionId=unestvsstmq0nxbe0053edeb; auth=0102535F805D48C2D608FE531FEA8711C3D6080016680073006F00660061006E006500700079007A00760067002D0078006200730034006300640032003000670000012F00FFA72219F26454785012FC48D22FD0DE6A9DFD3FB7")
                        .addHeader("cache-control", "no-cache")
                        .addHeader("postman-token", "9282cee6-4c60-4761-a5af-3422be56be9a")
                        .build();

                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, final IOException e) {
                        final TextView tv = findViewById(R.id.list);
//                        tv.postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                try {
//                                    tv.setText(e.getMessage());
//                                } catch (Exception e) {
//                                    e.printStackTrace();
//                                }
//
//                            }
//                        },0);

                    }

                    @Override
                    public void onResponse(Call call, final Response response) throws IOException {
                        final TextView tv = findViewById(R.id.list);
                        final String res = response.body().string();
                        Log.i(TAG, "onResponse: " + res);
                        LoginActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    tv.append(JsonFormateUtil.formatJson(res) + "\n");
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });

                    }
                });


            }
        });


        findViewById(R.id.pass).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {


                EditText pasid = findViewById(R.id.passId);


                Toast.makeText(LoginActivity.this, "pass start ing", Toast.LENGTH_SHORT).show();

                //点击获取个人信息

                OkHttpClient client = new OkHttpClient();

                String url = String.format("http://www.ahjxjy.cn/study/studying/recordVideoPosition?courseOpenId=gbrqan2ppl1jeegaopet9g&cellId=%s&schoolCode=003&position=2004.29166", pasid.getText().toString().isEmpty() ? "nv9bad6poltnuzclvgtc-a" : pasid.getText().toString());

                final Request request = new Request.Builder()
//                        .url("http://www.ahjxjy.cn/study/studying/recordVideoPosition?courseOpenId=gbrqan2ppl1jeegaopet9g&cellId=“+ pasid.getText()+”+“&schoolCode=003&position=2004.29166").method("POST", new RequestBody() {
                        .url(url).method("POST", new RequestBody() {
                            @Override
                            public MediaType contentType() {
                                return null;
                            }

                            @Override
                            public void writeTo(BufferedSink sink) throws IOException {

                            }
                        })
                        .addHeader("Cookie", "ASP.NET_SessionId=unestvsstmq0nxbe0053edeb; auth=0102535F805D48C2D608FE531FEA8711C3D6080016680073006F00660061006E006500700079007A00760067002D0078006200730034006300640032003000670000012F00FFA72219F26454785012FC48D22FD0DE6A9DFD3FB7")
                        .addHeader("cache-control", "no-cache")
                        .addHeader("postman-token", "9282cee6-4c60-4761-a5af-3422be56be9a")
                        .build();

                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, final IOException e) {
                        final TextView tv = findViewById(R.id.passLog);
                        tv.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    tv.setText(e.getMessage());
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            }
                        }, 0);

                    }

                    @Override
                    public void onResponse(Call call, final Response response) throws IOException {
                        final TextView tv = findViewById(R.id.passLog);
                        final String res = response.body().string();
                        Log.i(TAG, "onResponse: " + res);
                        LoginActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    tv.append(JsonFormateUtil.formatJson(res) + "\n");
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });

                    }
                });


            }
        });


    }

    private void populateAutoComplete() {
        if (!mayRequestContacts()) {
            return;
        }

        getLoaderManager().initLoader(0, null, this);
    }

    private boolean mayRequestContacts() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (checkSelfPermission(READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        if (shouldShowRequestPermissionRationale(READ_CONTACTS)) {
            Snackbar.make(mEmailView, R.string.permission_rationale, Snackbar.LENGTH_INDEFINITE)
                    .setAction(android.R.string.ok, new View.OnClickListener() {
                        @Override
                        @TargetApi(Build.VERSION_CODES.M)
                        public void onClick(View v) {
                            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
                        }
                    });
        } else {
            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
        }
        return false;
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_READ_CONTACTS) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                populateAutoComplete();
            }
        }
    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     * <p>
     * ASP.NET_SessionId	unestvsstmq0nxbe0053edeb
     * auth	01027AD1C8BA42C2D608FE7A9132E50BC3D6080016680073006F00660061006E006500700079007A00760067002D0078006200730034006300640032003000670000012F00FFA8FCA561B98D2F2F6F124DA06D560E6D13C92037
     */
    private void attemptLogin() {


        RequestBody body = new FormBody.Builder()
                .build();

        String userKey = "350104199603064428";

        String pwd = "064428";
        String verifycode = "064428";


        if (!getInputText((TextView) findViewById(R.id.userName)).isEmpty()) {
            userKey = getInputText((TextView) findViewById(R.id.userName));
        }

        if (!getInputText((TextView) findViewById(R.id.password)).isEmpty()) {
            pwd = getInputText((TextView) findViewById(R.id.password));
        }
        if (!getInputText((TextView) findViewById(R.id.verifycode)).isEmpty()) {
            verifycode = "&verifycode=" + getInputText((TextView) findViewById(R.id.verifycode));
        }
        String userName = "stu_" + userKey;
        //verifycode

        String url = String.format("http://www.ahjxjy.cn/users/login?stuOrTea=stu_&userKey=%s&username=%s&pwd=%s", userKey, userName, pwd, verifycode);


        Request request = new Request.Builder()

//                .url("http://www.ahjxjy.cn/users/login?stuOrTea=stu_&userKey=350104199603064428&username=stu_350104199603064428&pwd=064428")
                .url(url)
//                .url("http://www.ahjxjy.cn/users/login")
                .post(body)
//                .addHeader("ASP.NET_SessionId", "unestvsstmq0nxbe0053edeb")
//                .addHeader("auth", "01027AD1C8BA42C2D608FE7A9132E50BC3D6080016680073006F00660061006E006500700079007A00760067002D0078006200730034006300640032003000670000012F00FFA8FCA561B98D2F2F6F124DA06D560E6D13C92037")
//                .addHeader("Cookie", "ASP.NET_SessionId=unestvsstmq0nxbe0053edeb; auth=0102535F805D48C2D608FE531FEA8711C3D6080016680073006F00660061006E006500700079007A00760067002D0078006200730034006300640032003000670000012F00FFA72219F26454785012FC48D22FD0DE6A9DFD3FB7")
                .addHeader("cache-control", "no-cache")
                .addHeader("postman-token", "3a8e4572-e423-92c3-bcef-f87c898f2968")

                .build();

            HttpManager.getInstance().doRequest(request, new HttpManager.OnResponse() {


                @Override
                public void onResponse(Call call, Response response, String result) {


                    Log.i(TAG, "result=>\n" + JsonFormateUtil.formatJson(result));

                    final TextView tv = findViewById(R.id.loginLog);
                    String pattern = "欢迎您\\S*?！";
                    Pattern r = Pattern.compile(pattern);
                    Matcher m = r.matcher(result);


                    String str = "登录失败";
                    if (m.find()) {
                        System.out.println("Found value: " + m.group(0));
                        str = m.group(0);
                    } else {
                        System.out.println("NO MATCH");
                        str = "未发现登录信息";


                        donlowVerCode(verifycodeImg, HttpManager.getInstance().getClient());
                        verifycodeImg.setOnClickListener(new OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                donlowVerCode(verifycodeImg, HttpManager.getInstance().getClient());
                            }
                        });

                    }
                    tv.setText(str);

                }
            });


    }

    private void donlowVerCode(final ImageView verifycodeImg, OkHttpClient client) {

        Request request = new Request.Builder()

//                .url("http://www.ahjxjy.cn/users/login?stuOrTea=stu_&userKey=350104199603064428&username=stu_350104199603064428&pwd=064428")
                .url("http://www.ahjxjy.cn/users/verifycode")
//                .url("http://www.ahjxjy.cn/users/login")

//                .addHeader("ASP.NET_SessionId", "unestvsstmq0nxbe0053edeb")
//                .addHeader("auth", "01027AD1C8BA42C2D608FE7A9132E50BC3D6080016680073006F00660061006E006500700079007A00760067002D0078006200730034006300640032003000670000012F00FFA8FCA561B98D2F2F6F124DA06D560E6D13C92037")
//                .addHeader("Cookie", "ASP.NET_SessionId=unestvsstmq0nxbe0053edeb; auth=0102535F805D48C2D608FE531FEA8711C3D6080016680073006F00660061006E006500700079007A00760067002D0078006200730034006300640032003000670000012F00FFA72219F26454785012FC48D22FD0DE6A9DFD3FB7")
                .addHeader("cache-control", "no-cache")
                .addHeader("postman-token", "3a8e4572-e423-92c3-bcef-f87c898f2968")

                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                InputStream is = response.body().byteStream();


                final Bitmap bitmap = BitmapFactory.decodeStream(is);

                verifycodeImg.post(new Runnable() {
                    @Override
                    public void run() {
                        verifycodeImg.setImageBitmap(bitmap);


                    }
                });


            }
        });


    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this,
                // Retrieve data rows for the device user's 'profile' contact.
                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY), ProfileQuery.PROJECTION,

                // Select only email addresses.
                ContactsContract.Contacts.Data.MIMETYPE +
                        " = ?", new String[]{ContactsContract.CommonDataKinds.Email
                .CONTENT_ITEM_TYPE},

                // Show primary email addresses first. Note that there won't be
                // a primary email address if the user hasn't specified one.
                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        List<String> emails = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            emails.add(cursor.getString(ProfileQuery.ADDRESS));
            cursor.moveToNext();
        }

        addEmailsToAutoComplete(emails);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }

    private void addEmailsToAutoComplete(List<String> emailAddressCollection) {
        //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(LoginActivity.this,
                        android.R.layout.simple_dropdown_item_1line, emailAddressCollection);

        mEmailView.setAdapter(adapter);
    }


    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int ADDRESS = 0;
        int IS_PRIMARY = 1;
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final String mPassword;

        UserLoginTask(String email, String password) {
            mEmail = email;
            mPassword = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            try {
                // Simulate network access.
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                return false;
            }

            for (String credential : DUMMY_CREDENTIALS) {
                String[] pieces = credential.split(":");
                if (pieces[0].equals(mEmail)) {
                    // Account exists, return true if the password matches.
                    return pieces[1].equals(mPassword);
                }
            }

            // TODO: register the new account here.
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);

            if (success) {
                finish();
            } else {
                mPasswordView.setError(getString(R.string.error_incorrect_password));
                mPasswordView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }


    public String getInputText(TextView textView) {
        return textView.getText().toString();

    }
}

