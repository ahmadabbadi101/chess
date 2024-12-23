package frontend;

public interface Composite <T> extends Component<T> {
    public Component getChild();
    public void setChild(Component child);

}
