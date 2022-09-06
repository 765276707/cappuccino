package com.github.xzb617.cappuccino.server.domain.vo;

import com.github.xzb617.cappuccino.server.cluster.ClusterNode;

import java.util.List;

public class ClusterInfo {

    private boolean enabled;

    private List<ClusterNode> clusters;

    public ClusterInfo() {
    }

    public ClusterInfo(boolean enabled) {
        this.enabled = enabled;
    }

    public ClusterInfo(boolean enabled, List<ClusterNode> clusters) {
        this.enabled = enabled;
        this.clusters = clusters;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public List<ClusterNode> getClusters() {
        return clusters;
    }

    public void setClusters(List<ClusterNode> clusters) {
        this.clusters = clusters;
    }
}
