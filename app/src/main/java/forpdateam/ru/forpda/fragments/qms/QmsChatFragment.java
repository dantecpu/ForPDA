package forpdateam.ru.forpda.fragments.qms;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;

import forpdateam.ru.forpda.R;
import forpdateam.ru.forpda.api.Api;
import forpdateam.ru.forpda.api.qms.models.QmsChatModel;
import forpdateam.ru.forpda.fragments.TabFragment;
import forpdateam.ru.forpda.fragments.qms.adapters.QmsChatAdapter;
import forpdateam.ru.forpda.utils.IntentHandler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by radiationx on 25.08.16.
 */
public class QmsChatFragment extends TabFragment {
    public final static String defaultTitle = "Чат";
    public final static String USER_ID_ARG = "USER_ID_ARG";
    public final static String USER_AVATAR_ARG = "USER_AVATAR_ARG";
    public final static String THEME_ID_ARG = "THEME_ID_ARG";
    public final static String TAB_TAG_FOR_REMOVE = "TAB_TAG_FOR_REMOVE";
    private String userId;
    private String avatarUrl;
    private String themeId;
    private RecyclerView recyclerView;
    private QmsChatAdapter adapter;

    private QmsChatAdapter.OnItemClickListener onItemClickListener = (view1, position, adapter1) -> {
        Toast.makeText(getContext(), "ONCLICK " + position, Toast.LENGTH_SHORT).show();
    };
    private QmsChatAdapter.OnLongItemClickListener onLongItemClickListener = (view1, position, adapter1) -> {
        Toast.makeText(getContext(), "ON LONG CLICK " + position, Toast.LENGTH_SHORT).show();
    };

    @Override
    public String getDefaultTitle() {
        return defaultTitle;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            userId = getArguments().getString(USER_ID_ARG);
            themeId = getArguments().getString(THEME_ID_ARG);
            avatarUrl = getArguments().getString(USER_AVATAR_ARG);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initBaseView(inflater, container);
        inflater.inflate(R.layout.fragment_qms_chat, (ViewGroup) view.findViewById(R.id.fragment_content), true);
        recyclerView = (RecyclerView) findViewById(R.id.qms_chat);
        viewsReady();
        tryShowAvatar();
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        //llm.setStackFromEnd(true);
        recyclerView.setLayoutManager(llm);
        return view;
    }

    private void tryShowAvatar() {
        if (avatarUrl != null) {
            ImageLoader.getInstance().displayImage(avatarUrl, toolbarImageView);
            toolbarImageView.setVisibility(View.VISIBLE);
            toolbarImageView.setOnClickListener(view1 -> IntentHandler.handle("http://4pda.ru/forum/index.php?showuser=" + userId));
        } else {
            toolbarImageView.setVisibility(View.GONE);
        }
    }

    @Override
    public void loadData() {
        getCompositeDisposable().add(Api.Qms().getChat(userId, themeId)
                .onErrorReturn(throwable -> {
                    throwable.printStackTrace();
                    return new QmsChatModel();
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onLoadChat, throwable -> {
                    Toast.makeText(getContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
                }));
    }


    private void onLoadChat(QmsChatModel chat) {
        adapter = new QmsChatAdapter(chat.getChatItemsList(), getContext());
        adapter.setOnItemClickListener(onItemClickListener);
        adapter.setOnLongItemClickListener(onLongItemClickListener);
        recyclerView.setAdapter(adapter);
        setTitle(chat.getTitle());
        setSubtitle(chat.getNick());
        if (avatarUrl == null) {
            avatarUrl = chat.getAvatarUrl();
            tryShowAvatar();
            //TabManager.getInstance().remove(getParentTag());
        }
        recyclerView.scrollToPosition(adapter.getItemCount() - 1);
    }

}