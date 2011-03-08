package hopur20.blownaway;

public interface BombStateListener {
	
	/*
	 * Kallað þegar sprengjan springur.
	 */
	public abstract void onBombExploded();
	
	/*
	 * Kallað þegar sprengjan er aftengd
	 * timeRemaining er fjöldi sekúndna sem eftir voru þegar hún var aftengd
	 */
	public abstract void onBombDefused(long timeRemaining);
	/*
	 * Kallað þegar klukkan í sprengjunni tifar.
	 * timeRemaining er tíminn sem eftir er í sekúndum.
	 */
	public abstract void onTick(long timeRemaining);

}
