package com.chess.network;

/**
 * Represents a host's details.
 * Includes the host's ip and port.
 * @author Wincent Stålbert Holm
 * @version 2022-03-02
 */
public class HostDetails {
    /**
     * The host's ip.
     */
    private String ip;

    /**
     * The host's port.
     */
    private int port;

    /**
     * Constructs host details from an ip and port.
     * 
     * @param ip the ip address
     * @param port the port
     */
    public HostDetails(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    /**
     * Constructs host details from a port, setting the ip to null.
     * 
     * @param port the port
     */
    public HostDetails(int port) {
        this.ip = null;
        this.port = port;
    }

    /**
     * Constructs host details from a string representation of the host details.
     * 
     * Expected format: "ip:port", for example "localhost:1234".
     * 
     * @param hostDetails the string representation of the host details
     */
    public HostDetails(String hostDetails) throws NumberFormatException {
        // If there is a ':', then it is an IP address, otherwise it is just a port number
        if (hostDetails.contains(":")) {
            this.ip = hostDetails.substring(0, hostDetails.indexOf(":"));
            this.port = Integer.parseInt(hostDetails.substring(hostDetails.indexOf(":") + 1));
        } else {
            this.ip = null;
            this.port = Integer.parseInt(hostDetails);
        }
    }

    /**
     * Gets the ip address.
     * 
     * @return the ip address
     */
    public String getIp() {
        return ip;
    }

    /**
     * Gets the port.
     * 
     * @return the port
     */
    public int getPort() {
        return port;
    }

    /**
     * Gets the string representation of the host details.
     * 
     * @return the string representation of the host details
     */
    @Override
    public String toString() {
        if (ip == null) {
            return String.valueOf(port);
        } else {
            return ip + ":" + port;
        }
    }
}
