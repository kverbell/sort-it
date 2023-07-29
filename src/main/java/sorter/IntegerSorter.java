package sorter;

import java.util.ArrayList;
import java.util.List;

public class IntegerSorter implements FileSorter {
    private List<List<String>> fileContents;
    private SortOrder sortOrder;
    private boolean invalidDataDetected;

    public IntegerSorter(List<List<String>> fileContents, SortOrder sortOrder) {
        this.fileContents = fileContents;
        this.sortOrder = sortOrder;
    }

    @Override
    public List<Integer> sort() {

        List<Integer> mergedList = new ArrayList<>();

        invalidDataDetected = false;

        for (List<String> lines : fileContents) {
            for (String line : lines) {
                try {
                    int number = Integer.parseInt(line.trim());
                    mergedList.add(number);
                } catch (NumberFormatException e) {
                    invalidDataDetected = true;
                }
            }
        }

        if (invalidDataDetected) {
            System.out.println("Внимание! Данные, которые не соответствовали заявленным критериям сортировки, были пропущены.");
        }

        mergeSort(mergedList, sortOrder);
        return mergedList;
    }

    private void mergeSort(List<Integer> mergedList, SortOrder sortOrder) {
        if (mergedList.size() > 1) {
            int mid = mergedList.size() / 2;
            List<Integer> leftHalf = new ArrayList<>(mergedList.subList(0, mid));
            List<Integer> rightHalf = new ArrayList<>(mergedList.subList(mid, mergedList.size()));

            mergeSort(leftHalf, sortOrder);
            mergeSort(rightHalf, sortOrder);

            merge(mergedList, leftHalf, rightHalf, sortOrder);
        }
    }

    private void merge(List<Integer> mergedList, List<Integer> leftHalf, List<Integer> rightHalf, SortOrder sortOrder) {
        int leftIndex = 0;
        int rightIndex = 0;
        int mergeIndex = 0;

        while (leftIndex < leftHalf.size() && rightIndex < rightHalf.size()) {
            int leftValue = leftHalf.get(leftIndex);
            int rightValue = rightHalf.get(rightIndex);

            if (sortOrder == SortOrder.ASCENDING) {
                if (leftValue <= rightValue) {
                    mergedList.set(mergeIndex++, leftValue);
                    leftIndex++;
                } else {
                    mergedList.set(mergeIndex++, rightValue);
                    rightIndex++;
                }
            } else {
                if (leftValue >= rightValue) {
                    mergedList.set(mergeIndex++, leftValue);
                    leftIndex++;
                } else {
                    mergedList.set(mergeIndex++, rightValue);
                    rightIndex++;
                }
            }
        }

        while (leftIndex < leftHalf.size()) {
            mergedList.set(mergeIndex++, leftHalf.get(leftIndex++));
        }

        while (rightIndex < rightHalf.size()) {
            mergedList.set(mergeIndex++, rightHalf.get(rightIndex++));
        }
    }
}
