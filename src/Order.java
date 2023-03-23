public class Order {
    private Long id;
    private Double price;
    private Character side;
    private Long size;

    public Order() {}

    public Order(Long id, Double price, Character side, Long size) {
        this.id = id;
        this.price = price;
        this.side = side;
        this.size = size;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Character getSide() {
        return side;
    }

    public void setSide(Character side) {
        this.side = side;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }
}