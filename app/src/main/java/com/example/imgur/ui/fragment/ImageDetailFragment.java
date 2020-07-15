package com.example.imgur.ui.fragment;

import android.view.View;

import com.example.imgur.R;
import com.example.imgur.base.BaseFragment;
import com.example.imgur.databinding.FragmentImageDetailsLayoutBinding;
import com.example.imgur.model.Image;
import com.example.imgur.repository.CommentsRepository;
import com.example.imgur.ui.viewmodel.ImageDetailsViewModel;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import javax.inject.Inject;

public class ImageDetailFragment extends BaseFragment<FragmentImageDetailsLayoutBinding, ImageDetailsViewModel> {
    CommentsRepository commentsRepository;

    @Override
    public void onReady() {
        binding = getViewDataBinding();
        commentsRepository = new CommentsRepository(Objects.requireNonNull(getContext()));
        binding.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigationController.goBack();
            }
        });
        if (imageData != null) {

            if (commentsRepository.getTask(Objects.requireNonNull(imageData.getId()))!=null && commentsRepository.getTask(Objects.requireNonNull(imageData.getId())).getId().equals(imageData.getId())) {
                binding.edtSearchImage.setText(commentsRepository.getTask(imageData.getId()).getComments());
            }
            binding.progress.setVisibility(View.VISIBLE);
            Picasso.get().load(imageData.getLink()).into(binding.imgSearchImage, new Callback() {
                @Override
                public void onSuccess() {
                    binding.progress.setVisibility(View.GONE);
                }

                @Override
                public void onError(Exception e) {

                }
            });

            binding.tvTitle.setText(R.string.image_details);
            binding.btnSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    navigationController.goBack();
                    commentsRepository.insertTask(Objects.requireNonNull(imageData.getId()), Objects.requireNonNull(binding.edtSearchImage.getText()).toString());
                }
            });
        }
    }

    Image imageData;
    FragmentImageDetailsLayoutBinding binding;
    @Inject
    ImageDetailsViewModel imageDetailsViewModel;

    @NotNull
    @Override
    public ImageDetailsViewModel getViewModel() {
        return imageDetailsViewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_image_details_layout;
    }

    public void setData(Image imageData) {
        this.imageData = imageData;
    }
}
