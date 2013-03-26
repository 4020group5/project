package hci2.group5.project;

import hci2.group5.project.sideButton.SideButtonClickListenerFactory;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View mapFragment = findViewById(R.id.mapFragment);

        View buttons = findViewById(R.id.navAndSearchButtons);
        ImageButton navButton = (ImageButton) findViewById(R.id.navButton);
        ImageButton searchButton = (ImageButton) findViewById(R.id.searchButton);

        View navPane = findViewById(R.id.navPane);
        View searchPane = findViewById(R.id.searchPane);

		navButton.setOnClickListener(SideButtonClickListenerFactory.getNavOne(navPane, buttons));
		searchButton.setOnClickListener(SideButtonClickListenerFactory.getSearchOne(searchPane, buttons));
    }
}
