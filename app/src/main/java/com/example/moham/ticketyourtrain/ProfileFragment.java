package com.example.moham.ticketyourtrain;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by moham on 5/16/2016.
 */
public class ProfileFragment extends Fragment {
    TextView phonetxt, nametxt, balancetxt, datetxt ,gendertxt;
    ImageView imgview;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_profile, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        DBController controller = new DBController(getActivity());
        phonetxt = (TextView) getActivity().findViewById(R.id.phone_txt_profile);
        nametxt = (TextView) getActivity().findViewById(R.id.name_txt_profile);
        datetxt = (TextView) getActivity().findViewById(R.id.date_txt_profile);
        balancetxt = (TextView) getActivity().findViewById(R.id.balance_txt_profile);
        gendertxt = (TextView) getActivity().findViewById(R.id.gender_txt_profile);
        imgview = (ImageView) getActivity().findViewById(R.id.img_view);

        UserModel getuser = controller.getuser(MainActivity.Logged_user.getName());
        try {
            byte[] image = getuser.getImage();
            Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
            imgview.setImageBitmap(bitmap);
        } catch (Exception d) {
            Toast.makeText(getActivity(), d.getLocalizedMessage() + "", Toast.LENGTH_SHORT).show();
        }
        phonetxt.setText(getuser.getPhone() + "");
        nametxt.setText(getuser.getName() + "");
        balancetxt.setText(getuser.getBalance() + "");
        datetxt.setText(getuser.getBirth_date() + "");
        gendertxt.setText(getuser.getType()+"");

    }
}
