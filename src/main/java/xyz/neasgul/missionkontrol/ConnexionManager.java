package xyz.neasgul.missionkontrol;

import krpc.client.Connection;
import krpc.client.RPCException;
import krpc.client.Stream;
import krpc.client.StreamException;
import krpc.client.services.KRPC;
import krpc.client.services.SpaceCenter;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Benoit on 27/04/2016.
 */
public class ConnexionManager {
    private static ConnexionManager ourInstance;
    private static Connection mConnection;
    private static String mConnexionName;
    private static String mAddr;
    private static int mRpcPort;
    private static int mStreamPort;
    private static KRPC mKRPC;
    private static SpaceCenter mSpaceCenter;

    private static ArrayList<Stream> mStreamArray;

    /**
     * Get an instance of a Connexion manager
     * @return the instance of the Connexion manager
     */
    public static ConnexionManager getInstance() {

        if (ourInstance == null){
            ourInstance = new ConnexionManager();
        }
        return ourInstance;
    }

    private ConnexionManager(){
        mStreamArray = new ArrayList<Stream>();
    }

    /**
     * Use it for open a connexion to the krpc server
     * @param name the name of the connexion
     * @param addr the address of the server
     * @param rpcport the rpc port of this connexion
     * @param streamport the port for the data stream
     * @return a boolean for know if the connexion is establish
     * @throws IOException
     */
    public boolean OpenConnexion(String name, String addr, int rpcport, int streamport) throws IOException {
        mConnexionName = name;
        mAddr = addr;
        mRpcPort = rpcport;
        mStreamPort = streamport;
        mConnection = Connection.newInstance(mConnexionName, mAddr, mRpcPort, mStreamPort);

        if(mConnection == null){ return false; }
        else {
            mKRPC = KRPC.newInstance(mConnection);
            mSpaceCenter = SpaceCenter.newInstance(mConnection);
            return true;
        }
    }
    /**
     * Use it for open a connexion to the krpc server
     * @param name the name of the connexion
     * @param addr the address of the server
     * @return a boolean for know if the connexion is establish
     * @throws IOException
     */
    public boolean OpenConnexion(String name, String addr) throws IOException {
        return OpenConnexion(name,addr,50000,50001);
    }

    /**
     * A method for close the current connexion to the server
     * @return a boolean for know if the closing is successful
     */
    public boolean CloseConnexion(){
        try {
            mConnection.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Use for create a data stream from the server
     * @param clazz the object from API for the asked data
     * @param method the method of the clazz for the asked data
     * @param args some optional argument of the method asked
     * @return A {@link Stream} of the asked data.
     * @throws StreamException
     * @throws IOException
     * @throws RPCException
     */
    public Stream addStream(Class clazz, String method, Object... args) throws StreamException, IOException, RPCException {
        Stream stream = mConnection.addStream(clazz,method,args);
        mStreamArray.add(stream);
        return stream;
    }

    /**
     * For stop a data stream
     * @param stream that we want to close
     * @throws IOException
     * @throws RPCException
     */
    public void removeStream(Stream stream) throws IOException, RPCException {
        if(stream != null)
        {
            mStreamArray.remove(stream);
            stream.remove();
        }
    }

    public SpaceCenter getSpaceCenter() {
        return mSpaceCenter;
    }

    public ArrayList<Stream> getStream(){return mStreamArray;}

    public Connection getConnection() {
        return mConnection;
    }

    public KRPC getKRPC() {
        return mKRPC;
    }

    public String getConnexionName() {
        return mConnexionName;
    }

    public String getAddr() {
        return mAddr;
    }

    public int getRpcPort() {
        return mRpcPort;
    }

    public int getmStreamPort() {
        return mStreamPort;
    }

}
