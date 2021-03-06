package demo.com.sharetext;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ShareActionProvider;
import android.widget.TextView;
import android.widget.Toast;


/**
 * @author Dharmendra Vaishnav
 */
public class ShareText extends Activity {

    private ShareActionProvider mShareActionProvider;
    private EditText shareEditText = null;
    private Intent sendIntent = new Intent();
    private ActionBar mActionbar = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_share_text);
        mActionbar = getActionBar();
        mActionbar.setDisplayShowTitleEnabled(false);
        addTextToActionBar();

        shareEditText = (EditText) findViewById(R.id.msg_to_share);
        //Added listener on the text box which update text to share
        shareEditText.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                String msgToShare = String.valueOf(shareEditText.getText());
                sendIntent.putExtra(Intent.EXTRA_TEXT, msgToShare);
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.share_text, menu);
        // Locate MenuItem with ShareActionProvider
        MenuItem item = menu.findItem(R.id.menu_item_share);

        // Fetch and store ShareActionProvider
        mShareActionProvider = (ShareActionProvider) item.getActionProvider();
        //Setting share intent to action provider
        mShareActionProvider.setShareIntent(shareIntent());
        return true;
    }
    public void addTextToActionBar()
    {
        mActionbar.setDisplayShowCustomEnabled( true );

        // Inflate the custom view
        LayoutInflater inflater = LayoutInflater.from( this );
        View header = inflater.inflate( R.layout.actionbar_header, null );

        //Retrieve custom created title and enable onClick listener
        TextView tv = (TextView) header.findViewById( R.id.program_title );
        tv.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //Start fetch contact activity
                Intent intent = new Intent(ShareText.this,ContactListActivity.class);
                startActivity(intent);
            }
        });

        mActionbar.setCustomView( header );


    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_item_share) {
            shareIntent();
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * This method prepare share Intent which have content type, content.
     *
     * @return share intent with chooser enabled
     */
    private Intent shareIntent() {
        sendIntent.setAction(Intent.ACTION_SEND);
        String msgToShare = String.valueOf(shareEditText.getText());
        sendIntent.putExtra(Intent.EXTRA_TEXT, msgToShare);
        sendIntent.setType("text/plain");
        return Intent.createChooser(sendIntent, getResources().getText(R.string.chooser_title));
    }
}