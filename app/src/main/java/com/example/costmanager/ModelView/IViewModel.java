package com.example.costmanager.ModelView;

/**
 * Represents an interface of our model.
 * gives more accessibility and flexibility to the code
 */
public interface IViewModel {

    public void getReport();
    public void addItem(final String itemName,final String category,final String cost,final String date);
    void showItems();
    void deleteItemVM(final String id);
}
