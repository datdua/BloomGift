import React, { useEffect, useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { fetchSellerInfo } from '../../../redux/actions/storeActions';
import './StoreProfile.css';

const StoreProfile = () => {
    const dispatch = useDispatch();
    const sellerInfo = useSelector(state => state.seller.sellerInfo);
    const [activeTab, setActiveTab] = useState('Thông tin cơ bản');

    useEffect(() => {
        const storeID = localStorage.getItem('storeID');
        dispatch(fetchSellerInfo(storeID));
    }, [dispatch]);

    const renderTabContent = () => {
        switch (activeTab) {
            case 'Thông tin cơ bản':
                return (
                    <div className="basic-info">
                        <h2>Thông tin cơ bản</h2>
                        <div className="info-row">
                            <span>Tên Shop:</span>
                            <span>{sellerInfo?.storeName || '0967630810phuc'}</span>
                        </div>
                        <div className="info-row">
                            <span>Logo của Shop:</span>
                            <div className="logo-container">
                                {sellerInfo?.storeAvatar ? (
                                    <img src={sellerInfo.storeAvatar} alt="Shop Logo" width="100"
                                        height="100"
                                        className="rounded-circle" />
                                ) : (
                                    <div className="default-logo">P</div>
                                )}
                            </div>

                            <ul>
                                <li>Kích thước hình ảnh tiêu chuẩn: Chiều rộng 300px, Chiều cao 300px</li>
                                <li>Dung lượng file tối đa: 2.0MB</li>
                                <li>Định dạng file được hỗ trợ: JPG, JPEG, PNG</li>
                            </ul>
                        </div>

                        <div className="info-row">
                            <span>Mô tả Shop:</span>
                            <span>Đảm bảo rằng bạn đã import và sử dụng đúng action fetchSellerInfo từ file storeActions.js, và rằng reducer của bạn đã được cấu hình đúng để xử lý action này và cập nhật state với thông tin cửa hàng.</span>
                            <span>{sellerInfo?.description || ''}</span>
                        </div>
                        <div className="info-row">
                            <span>Trạng thái Shop:</span>
                            <span>{sellerInfo?.storeStatus || ''}</span>
                        </div>
                    </div>
                );
            case 'Thông tin Thuế':
                return (
                    <div className="tax-info">
                        <h2>Thông tin Thuế</h2>
                        <div className="info-row">
                            <span>Email:</span>
                            <span>{sellerInfo?.email || 'N/A'}</span>
                        </div>
                        <div className="info-row">
                            <span>Địa chỉ:</span>
                            <span>{sellerInfo?.storeAddress || 'N/A'}</span>
                        </div>
                        <div className="info-row">
                            <span>Loại hình kinh doanh:</span>
                            <span>{sellerInfo?.type || 'N/A'}</span>
                        </div>
                        <div className="info-row">
                            <span>Mã số thuế:</span>
                            <span>{sellerInfo?.taxNumber || 'N/A'}</span>
                        </div>
                    </div>
                );
            case 'Thông tin Khác':
                return (
                    <div className="tax-info">
                        <h2>Thông tin Định Danh</h2>
                        <div className="info-row">
                            <span>CCCD: </span>
                            <span>{sellerInfo?.identityCard || 'N/A'}</span>
                        </div>
                        <div className="info-row">
                            <span>Tên CCCD: </span>
                            <span>{sellerInfo?.identityName || 'N/A'}</span>
                        </div>
                        <h2>Thông tin Ngân Hàng</h2>
                        <div className="info-row">
                            <span>Tên ngân hàng: </span>
                            <span>{sellerInfo?.bankAccountName || 'N/A'}</span>
                        </div>
                        <div className="info-row">
                            <span>Số tài khoản: </span>
                            <span>{sellerInfo?.bankNumber || 'N/A'}</span>
                        </div>
                        <div className="info-row">
                            <span>Địa chỉ ngân hàng: </span>
                            <span>{sellerInfo?.bankAddress || 'N/A'}</span>
                        </div>
                    </div>
                );
            default:
                return null;
        }
    };

    return (
        <div className="store-profile">
            <div className="tab-header">
                {['Thông tin cơ bản', 'Thông tin Thuế', 'Thông tin Khác'].map(tab => (
                    <button
                        key={tab}
                        className={activeTab === tab ? 'active' : ''}
                        onClick={() => setActiveTab(tab)}
                    >
                        {tab}
                    </button>
                ))}
            </div>
            <div className="tab-content">
                {renderTabContent()}
            </div>
            <div className="action-buttons">
                <button className="view-shop">Xem Shop của tôi</button>
                <button className="edit">Chỉnh sửa</button>
            </div>
        </div>
    );
};

export default StoreProfile;