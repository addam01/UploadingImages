package testaccountlogin.rounin_rave.com.uploadingimages;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {
    private Uri fileUri;
    Button takePic;
    ImageView picture;
    private Bitmap bitmap;
    String picturePath;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        picture = (ImageView) view.findViewById(R.id.ImagePrev);
        takePic = (Button) view.findViewById(R.id.pic);
        takePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePicture();
            }
        });
        return view;
    }

    private void takePicture() {
        //Check Camera
        if (getActivity().getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){

            //Open Default Camera
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            //Start the image capture intent
            startActivityForResult(intent,0);
        }else {
            Toast.makeText(getActivity().getApplication(), "Camera not supported", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == 100 && requestCode == Activity.RESULT_OK) {
            Toast.makeText(getActivity().getApplication(),"Working",Toast.LENGTH_LONG).show();
            Bitmap imageBitmap = (Bitmap) data.getExtras().get("data");
            picture.setImageBitmap(imageBitmap);
        }

    }
}
