package de.mpg.cbs.core.laminar;

import de.mpg.cbs.utilities.*;
import de.mpg.cbs.structures.*;
import de.mpg.cbs.libraries.*;
import de.mpg.cbs.methods.*;


/*
 * @author Pierre-Louis Bazin
 */
public class LaminarProfileMeshing {

	private float[] layersImage;

	private float[] surfacePoints;
	private int[] surfaceTriangles;

	private int nx, ny, nz, nt, nxyz;
	private float rx, ry, rz;

	private float[][] sampledPoints;
	private int[][] sampledTriangles;
	
	// global variables
	private static final byte X = 0;
	private static final byte Y = 1;
	private static final byte Z = 2;

	// create inputs
	public final void setProfileSurfaceImage(float[] val) { layersImage = val; }
	public final void setSurfacePoints(float[] val) { surfacePoints = val; }
	public final void setSurfaceTriangles(int[] val) { surfaceTriangles = val; }
	
	public final void setDimensions(int x, int y, int z, int t) { nx=x; ny=y; nz=z; nt=t; nxyz=nx*ny*nz; }
	public final void setDimensions(int[] dim) { nx=dim[0]; ny=dim[1]; nz=dim[2]; nt=dim[3]; nxyz=nx*ny*nz; }
	
	public final void setResolutions(float x, float y, float z) { rx=x; ry=y; rz=z; }
	public final void setResolutions(float[] res) { rx=res[0]; ry=res[1]; rz=res[2]; }

	// to be used for JIST definitions, generic info / help
	public final String getPackage() { return "CBS Tools"; }
	public final String getCategory() { return "Laminar Analysis"; }
	public final String getLabel() { return "Profile Meshing"; }
	public final String getName() { return "ProfileMeshing"; }

	public final String[] getAlgorithmAuthors() { return new String[]{"Pierre-Louis Bazin","Juliane Dinse"}; }
	public final String getAffiliation() { return "Max Planck Institute for Human Cognitive and Brain Sciences"; }
	public final String getDescription() { return "Build a collection of cortical surfaces at given layers with matching vertices."; }
	public final String getLongDescription() { return getDescription(); }
		
	public final String getVersion() { return "3.1.0"; };
			
	// create outputs
	public final float[] getSampledSurfacePoints(int n) { return sampledPoints[n]; }
	public final int[] getSampledSurfaceTriangles(int n) { return sampledTriangles[n]; }
	
	public void execute(){
		
		int nlayers = nt-1;
		
		float[][] layers = new float[nlayers+1][nxyz];
		for (int x=0;x<nx;x++) for (int y=0;y<ny;y++) for (int z=0;z<nz;z++) for (int l=0;l<=nlayers;l++) {
			int xyz = x+nx*y+nx*ny*z;
			layers[l][xyz] = layersImage[xyz+nxyz*l];
		}
		layersImage = null;
		
		int npt = surfacePoints.length/3;
		int ntr = surfaceTriangles.length/3;
		
		// main algorithm
		CorticalProfile profile = new CorticalProfile(nlayers, nx, ny, nz, rx, ry, rz);
		
		sampledPoints = new float[nlayers+1][3*npt];
		for (int p=0; p<npt; p++) {
			profile.computeTrajectory(layers, surfacePoints[3*p+X], surfacePoints[3*p+Y], surfacePoints[3*p+Z]);
			
			for (int l=0;l<nlayers+1;l++) {
				sampledPoints[l][3*p+X] = profile.getPt(l)[X];
				sampledPoints[l][3*p+Y] = profile.getPt(l)[Y];
				sampledPoints[l][3*p+Z] = profile.getPt(l)[Z];
			}
		}
		sampledTriangles = new int[nlayers+1][3*ntr];
		for (int t=0;t<ntr;t++) {
			for (int l=0;l<nlayers+1;l++) {
				sampledTriangles[l][3*t+0] = surfaceTriangles[3*t+0];
				sampledTriangles[l][3*t+1] = surfaceTriangles[3*t+1];
				sampledTriangles[l][3*t+2] = surfaceTriangles[3*t+2];
			}
		}

	}


}