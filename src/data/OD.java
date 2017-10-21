package data;

public class OD
{
	
	private final String id;
	private final int nbEffectuer; //le nombre de fois que cette OD doit etre effectuee
	private final double coef; //valeur de l'OD dans la fonction objective
	
	
	public OD(String id, double coef, int nb)
	{
		this.id = id;
		this.nbEffectuer = nb;
		this.coef = coef;
	}

	
//	public OD(String id, double coef)
//	{
//		this.id = id;
//		this.coef = coef;
//		this.nbEffectuer = -1;
//	}
	
	public boolean isThisOD(Task t)
	{
		if(id.equals(t.getCategories()))
			return true;
		return false;
	}
	
	public String getId() {
		return id;
	}


	public int getNbEffectuer() {
		return nbEffectuer;
	}


	public double getCoef() {
		return coef;
	}
	
	

}
