package com.example.idealhouse;

import java.util.List;

public class ViviendaResponse {
        public ViviendaResponse() {


                this.elementList.add(new ElementList());
                this.total = 1;
                this.totalPages = 1;
                this.actualPage = 1;
                this.itemsPerPage = 10;
                this.numPaginations = 1;

        }

        private List<ElementList> elementList;
        private long total;
        private long totalPages;
        private long actualPage;
        private long itemsPerPage;
        private long numPaginations;
        private boolean hiddenResults;
        private List<String> summary;
        private boolean paginable;
        private long upperRangePosition;
        private long lowerRangePosition;

        public List<ElementList> getElementList() { return elementList; }
        public void setElementList(List<ElementList> value) { this.elementList = value; }
        public long getTotal() { return total; }
        public void setTotal(long value) { this.total = value; }
        public long getTotalPages() { return totalPages; }
        public void setTotalPages(long value) { this.totalPages = value; }
        public long getActualPage() { return actualPage; }
        public void setActualPage(long value) { this.actualPage = value; }
        public long getItemsPerPage() { return itemsPerPage; }
        public void setItemsPerPage(long value) { this.itemsPerPage = value; }
        public long getNumPaginations() { return numPaginations; }
        public void setNumPaginations(long value) { this.numPaginations = value; }
        public boolean getHiddenResults() { return hiddenResults; }
        public void setHiddenResults(boolean value) { this.hiddenResults = value; }
        public List<String> getSummary() { return summary; }
        public void setSummary(List<String> value) { this.summary = value; }
        public boolean getPaginable() { return paginable; }
        public void setPaginable(boolean value) { this.paginable = value; }


        public long getUpperRangePosition() { return upperRangePosition; }
        public void setUpperRangePosition(long value) { this.upperRangePosition = value; }
        public long getLowerRangePosition() { return lowerRangePosition; }
        public void setLowerRangePosition(long value) { this.lowerRangePosition = value; }


}
