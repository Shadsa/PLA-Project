package roles;

import java.util.ArrayList;
import java.util.Observable;

import roles.classe.Classe;

public class Army extends Observable{

	protected Joueur _joueur;
	protected ArrayList<Personnage> _personnages;
	protected World _world;

	public Army(World world, Joueur j)
	{
		_joueur = j;
		_personnages = new ArrayList<Personnage>();
		_world = world;
		world.addArmy(this);
	}

	public Joueur joueur() {
		return _joueur;
	}

	public ArrayList<Personnage> getPersonnages() {
		return _personnages;
	}

	public Personnage createPersonnage(int type, int x, int y, Personnage imageOF)
	{
		// WARNING faire plutot un get automate avec gestion d'erreur
		Personnage newPers = new Personnage(_joueur.automate(type), _joueur.automatec(type), x, y, imageOF, this, _joueur.classe(type));
		_personnages.add(newPers);
		setChanged();
		notifyObservers(newPers);
		return newPers;
	}

	public Personnage join(int type, Cardinaux from, Personnage imageOF)
	{
		switch(from)
		{
		case OUEST:case NORD:
			for(int x = 0; x<world().SizeX(); x++)
				for(int j = 0; j<world().SizeY()+1; j++)
				{
					if(world().isfree(x, world().SizeY()/2 + ((j%2==0)? 1 : -1) * j/2))
					{
						// WARNING faire plutot un get automate avec gestion d'erreur
						Personnage newPers = new Personnage(_joueur.automate(type), _joueur.automatec(type), x, world().SizeY()/2 + ((j%2==0)? 1 : -1) * j/2, imageOF, this, _joueur.classe(type));
						_personnages.add(newPers);
						setChanged();
						notifyObservers(newPers);
						return newPers;
					}
				}
		break;
		case EST:case SUD:
			for(int x = world().SizeX()-1; x>=0; x--)
				for(int j = 0; j<world().SizeY()+1; j++)
				{
					if(world().isfree(x, world().SizeY()/2 + ((j%2==0)? 1 : -1) * j/2))
					{
						// WARNING faire plutot un get automate avec gestion d'erreur
						Personnage newPers = new Personnage(_joueur.automate(type), _joueur.automatec(type), x, world().SizeY()/2 + ((j%2==0)? 1 : -1) * j/2, imageOF, this, _joueur.classe(type));
						_personnages.add(newPers);
						setChanged();
						notifyObservers(newPers);
						return newPers;
					}
				}
		break;
		/*case NORD:
			for(int y = 0; y<world().SizeY(); y++)
				for(int j = 0; j<world().SizeX()+1; j++)
				{
					if(world().isfree(world().SizeY()/2 + ((j%2==0)? 1 : -1) * j/2, y))
					{
						// WARNING faire plutot un get automate avec gestion d'erreur
						Personnage newPers = new Personnage(_joueur.automate(type), _joueur.automatec(type), world().SizeX()/2 + ((j%2==0)? 1 : -1) * j/2, y, imageOF, this, _joueur.classe(type));
						_personnages.add(newPers);
						setChanged();
						notifyObservers(newPers);
						return newPers;
					}
				}
		break;
		case SUD:
			for(int y = world().SizeY()-1; y<0; y--)
				for(int j = 0; j<world().SizeX()+1; j++)
				{
					if(world().isfree(world().SizeY()/2 + ((j%2==0)? 1 : -1) * j/2, y))
					{
						// WARNING faire plutot un get automate avec gestion d'erreur
						Personnage newPers = new Personnage(_joueur.automate(type), _joueur.automatec(type), world().SizeX()/2 + ((j%2==0)? 1 : -1) * j/2, y, imageOF, this, _joueur.classe(type));
						_personnages.add(newPers);
						setChanged();
						notifyObservers(newPers);
						return newPers;
					}
				}
		break;*/
		}
		return null;
	}

	public int nbUnit(int type){
		int count=0;
		for(Personnage p : _personnages)
			if(p.getUnite()==type)
				count++;
		return count;
	}

	public int ratioUnit(int type){
		return (100*(nbUnit(type))/_personnages.size());
	}

	public World world() {
		return _world;
	}

}
