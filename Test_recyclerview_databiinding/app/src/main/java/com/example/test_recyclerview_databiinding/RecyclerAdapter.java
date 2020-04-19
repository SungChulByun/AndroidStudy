package com.example.test_recyclerview_databiinding;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.test_recyclerview_databiinding.databinding.ItemMemoBinding;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
	private MemoViewModel viewModel;

	public RecyclerAdapter(MemoViewModel viewModel) {
		this.viewModel = viewModel;
	}

	@NonNull
	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		ItemMemoBinding
			binding = ItemMemoBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
		return new ItemViewHolder(binding);
	}

	@Override
	public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
		if (holder instanceof ItemViewHolder) {
			((ItemViewHolder) holder).bind(viewModel, position);
		}
	}


	@Override
	public int getItemCount() {
		return viewModel.getItems() == null ? 0 : viewModel.getItems().size();
	}


	public static class ItemViewHolder extends RecyclerView.ViewHolder {
		private ItemMemoBinding binding;

		public ItemViewHolder(ItemMemoBinding binding) {
			super(binding.getRoot());
			this.binding = binding;
		}

		public void bind(MemoViewModel viewModel, int pos) {
			binding.setViewmodel(viewModel);
			binding.setPos(pos);
			binding.executePendingBindings();
		}
	}
}
