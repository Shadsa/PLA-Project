package roles;

public enum States {

    AVANCE;

    private Cardinaux _Dir;

    private States() {
    	_Dir = null;
    }

    public Cardinaux Dir() {
        return _Dir;
    }

    public States init(Cardinaux Dir)
    {
    	_Dir = Dir;
    	return this;
    }
}