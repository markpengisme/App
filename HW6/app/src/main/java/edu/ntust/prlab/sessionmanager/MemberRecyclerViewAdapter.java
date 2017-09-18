package edu.ntust.prlab.sessionmanager;

import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import edu.ntust.prlab.sessionmanager.database.MemberContract;

/**
 * 用來幫助呈現成員清單的Adapter
 */
class MemberRecyclerViewAdapter extends RecyclerView.Adapter<MemberRecyclerViewAdapter.ViewHolder>
        implements ItemTouchHelperAdapter {

    /**
     * 在使用者滑動的時候，能夠呼叫相對應動作的Callback。
     */
    private OnDragListener onDragListener;
    private Cursor cursor;

    /**
     * 紀錄男性和女性符號的UTF-8文字
     */
    private static final String MALE_STRING = "\u2642";
    private static final String FEMALE_STRING = "\u2640";

    /**
     * 用來呈現男性和女性的圖案背景
     */
    private final Drawable MALE_BACKGROUND;
    private final Drawable FEMALE_BACKGROUND;

    MemberRecyclerViewAdapter(OnDragListener onDragListener, Drawable maleBackground, Drawable femaleBackground) {
        this.onDragListener = onDragListener;
        this.MALE_BACKGROUND = maleBackground;
        this.FEMALE_BACKGROUND = femaleBackground;
    }

    /**
     * 需要刷新整個呈現資料的時候，透過將Cursor換掉以呈現新資料。
     */
    void swapCursor(Cursor cursor) {
        this.cursor = cursor;
        this.notifyDataSetChanged();
    }

    /**
     * 當ViewHolder不夠用的時候，就會呼叫這邊產生新的ViewHolder。
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MemberRecyclerViewAdapter.ViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.item_member, parent, false));
    }

    /**
     * 當ViewHolder的呈現資料要更新的時候，就呼叫此函式，做相對應的動作把值綁訂到ViewHolder上。
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Member member = getMember(position);
        holder.idTextView.setText(String.valueOf(member.getId()));
        holder.nameTextView.setText(member.getName());
        holder.ageTextView.setText(String.valueOf(member.getAge()));
        if (Member.Gender.MALE == member.getGender()) {
            holder.genderTextView.setText(MALE_STRING);
            holder.genderTextView.setBackground(MALE_BACKGROUND);
        } else {
            holder.genderTextView.setText(FEMALE_STRING);
            holder.genderTextView.setBackground(FEMALE_BACKGROUND);
        }
    }

    /**
     * 全部資料的數量。
     */
    @Override
    public int getItemCount() {
        return this.cursor != null ? this.cursor.getCount() : 0;
    }

    /**
     * 配合ItemTouchHelper的Callback
     */
    @Override
    public void onItemDismiss(int position) {
        onDragListener.onDrag(getMember(position));
    }

    /**
     * 把Cursor的資料轉換成Member
     */
    private Member getMember(int position) {
        if (!cursor.moveToPosition(position)) return null;
        return new Member()
                .setId(cursor.getInt(cursor.getColumnIndex(MemberContract.MemberEntry._ID)))
                .setName(cursor.getString(cursor.getColumnIndex(MemberContract.MemberEntry.NAME)))
                .setAge(cursor.getInt(cursor.getColumnIndex(MemberContract.MemberEntry.AGE)))
                .setGender(Member.Gender.valueOf(cursor.getString(cursor.getColumnIndex(MemberContract.MemberEntry.GENDER))));
    }

    /**
     * 當頁面滑動的時候，所呼叫的Callback介面
     */
    interface OnDragListener {
        void onDrag(Member member);
    }

    /**
     * 用來代表呈現畫面物件的物件
     */
    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView idTextView;
        private TextView nameTextView;
        private TextView ageTextView;
        private TextView genderTextView;

        ViewHolder(View itemView) {
            super(itemView);
            idTextView = (TextView) itemView.findViewById(R.id.item_id);
            nameTextView = (TextView) itemView.findViewById(R.id.item_name);
            ageTextView = (TextView) itemView.findViewById(R.id.item_age);
            genderTextView = (TextView) itemView.findViewById(R.id.item_gender);
        }

    }

}
