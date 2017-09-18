package edu.ntust.prlab.sessionmanager;

import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import edu.ntust.prlab.sessionmanager.database.MemberContract;

import static edu.ntust.prlab.sessionmanager.database.MemberContract.ORDER_BY_AGE;
import static edu.ntust.prlab.sessionmanager.database.MemberContract.ORDER_BY_GENDER;
import static edu.ntust.prlab.sessionmanager.database.MemberContract.ORDER_BY_NAME;

public class MainActivity extends AppCompatActivity
        implements MemberRecyclerViewAdapter.OnDragListener, LoaderManager.LoaderCallbacks<Cursor> {

    private static final int LOADER_SEARCH_MEMBERS = 87;
    private static final String KEY_ORDER_BY = "order";

    private RecyclerView memberRecyclerView;
    private MemberRecyclerViewAdapter memberRecyclerViewAdapter;
    private ItemTouchHelper itemTouchHelper;

    private volatile Toast lastToastMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindUIComponents();
        getLoaderManager().restartLoader(LOADER_SEARCH_MEMBERS, null, this);
    }

    /**
     * 根據URI去查詢成員資料。
     */
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch (id) {
            case LOADER_SEARCH_MEMBERS:
                return new CursorLoader(this,
                        MemberContract.CONTENT_URI, null, null, null, args != null ? args.getString(KEY_ORDER_BY) : null);
        }

        return null;
    }

    /**
     * 如果查詢完畢就更新Adapter的Cursor。
     */
    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        switch (loader.getId()) {
            case LOADER_SEARCH_MEMBERS:
                memberRecyclerViewAdapter.swapCursor(data);
                break;
        }
    }

    /**
     * 如果查詢失敗就把Adapter呈現的Cursor清空。
     */
    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        switch (loader.getId()) {
            case LOADER_SEARCH_MEMBERS:
                memberRecyclerViewAdapter.swapCursor(null);
                break;
        }
    }

    /**
     * 綁定UI所需的物件，以及取得相對應需要的圖片資源。
     */
    private void bindUIComponents() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);

        memberRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_member);
        //根據不同Android版本取得圖片資源
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            memberRecyclerViewAdapter = new MemberRecyclerViewAdapter(
                    this,
                    ContextCompat.getDrawable(this, R.drawable.male_background),
                    ContextCompat.getDrawable(this, R.drawable.female_background)
            );
        } else {
            memberRecyclerViewAdapter = new MemberRecyclerViewAdapter(
                    this,
                    getResources().getDrawable(R.drawable.male_background),
                    getResources().getDrawable(R.drawable.female_background)
            );
        }
        memberRecyclerView.setAdapter(memberRecyclerViewAdapter);

        //用來設定滑動移除Member的Callback
        ItemTouchHelper.Callback callback = new ItemTouchHelper.SimpleCallback(
                ItemTouchHelper.UP | ItemTouchHelper.DOWN, ItemTouchHelper.START | ItemTouchHelper.END) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                memberRecyclerViewAdapter.onItemDismiss(viewHolder.getAdapterPosition());
            }
        };

        itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(memberRecyclerView);
    }

    /**
     * 有人滑動的時候，會反過頭來呼叫此函式。
     * 根據ContentProvider的介面去移除相對應ID的Member。
     */
    @Override
    public void onDrag(Member member) {
        Uri uri = ContentUris.withAppendedId(MemberContract.CONTENT_URI, member.getId());
        getContentResolver().delete(uri, null, null);
        getLoaderManager().restartLoader(LOADER_SEARCH_MEMBERS, null, this);
        displayMessage(member.getName() + getString(R.string.message_left));
    }

    /**
     * 當點擊新增Member的按鈕時，會建立彈出視窗，能夠輸入資料並新增。
     */
    public void onAddButtonClicked(View view) {
        createDialog().show();
    }

    private AlertDialog createDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = LayoutInflater.from(this);
        View contentView = inflater.inflate(R.layout.alert_add_member, memberRecyclerView, false);
        // 設定Dialog的標題和內容排版
        builder.setMessage(R.string.message_add_member)
                .setView(contentView);
        // 根據ID將內容排版的物件取出
        final EditText nameEditText = (EditText) contentView.findViewById(R.id.input_name);
        final EditText ageEditText = (EditText) contentView.findViewById(R.id.input_age);
        final RadioGroup genderRadioGroup = (RadioGroup) contentView.findViewById(R.id.radio_gender);
        return builder
                // 設定OK按鈕時，會根據內容物件所記載的訊息新增資料
                .setPositiveButton(R.string.message_sure, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        String name = String.valueOf(nameEditText.getText());
                        String age = String.valueOf(ageEditText.getText());
                        String gender = genderRadioGroup.getCheckedRadioButtonId() == R.id.radio_male ?
                                Member.Gender.MALE.name() : Member.Gender.FEMALE.name();

                        // 雖然xml有設定只能輸入數字，但還是檢查預期之外輸入
                        if (!age.matches("[1-9]+\\d*")) {
                            displayMessage(getString(R.string.message_invalid));
                            return;
                        }
                        //根據內容新增Member
                        ContentValues values = new ContentValues();
                        values.put(MemberContract.MemberEntry.NAME, name);
                        values.put(MemberContract.MemberEntry.AGE, age);
                        values.put(MemberContract.MemberEntry.GENDER, gender);
                        getContentResolver().insert(MemberContract.CONTENT_URI, values);

                        //重新讀取Member，然後顯示訊息給使用者
                        getLoaderManager().restartLoader(LOADER_SEARCH_MEMBERS, null, MainActivity.this);
                        displayMessage(name + getString(R.string.message_join));
                    }
                })
                // 當Cancel按鈕點即時，什麼都不做
                .setNegativeButton(R.string.message_cancel, null)
                .create();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /**
     * 依據使用者選取的排序條件，重新呼叫查詢Member的Loader。
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Bundle bundle = new Bundle();
        switch (item.getItemId()) {
            case R.id.action_sort_by_id:
                break;
            case R.id.action_sort_by_name:
                bundle.putString(KEY_ORDER_BY, ORDER_BY_NAME);
                break;
            case R.id.action_sort_by_gender:
                bundle.putString(KEY_ORDER_BY, ORDER_BY_GENDER);
                break;
            case R.id.action_sort_by_age:
                bundle.putString(KEY_ORDER_BY, ORDER_BY_AGE);
                break;
        }
        getLoaderManager().restartLoader(LOADER_SEARCH_MEMBERS, bundle, this);
        return super.onOptionsItemSelected(item);
    }

    /**
     * 用來回饋訊息給使用者的函式。
     */
    private void displayMessage(String message) {
        if (lastToastMessage != null) lastToastMessage.cancel();
        lastToastMessage = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        lastToastMessage.show();
    }

}
