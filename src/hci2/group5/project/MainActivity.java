package hci2.group5.project;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View mapFragment = findViewById(R.id.mapFragment);

        Button navButton = (Button) findViewById(R.id.navButton);
        Button searchButton = (Button) findViewById(R.id.searchButton);

        View sidePane = findViewById(R.id.sidePane);

        View.OnClickListener listener = new ClickListenerForSidePaneOpenClose(navButton, searchButton, sidePane);
		navButton.setOnClickListener(listener);
		searchButton.setOnClickListener(listener);
    }
}
